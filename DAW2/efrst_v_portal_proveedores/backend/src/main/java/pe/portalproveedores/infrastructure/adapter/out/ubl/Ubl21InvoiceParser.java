package pe.portalproveedores.infrastructure.adapter.out.ubl;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pe.portalproveedores.application.port.out.InvoiceXmlParserPort;
import pe.portalproveedores.domain.exception.InvoiceValidationException;
import pe.portalproveedores.domain.model.DetractionInfo;
import pe.portalproveedores.domain.model.InvoiceLine;
import pe.portalproveedores.domain.model.SupplierInvoice;
import pe.portalproveedores.domain.model.TaxBreakdown;
import pe.portalproveedores.domain.model.TaxIdentifier;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Ubl21InvoiceParser implements InvoiceXmlParserPort {

    private static final String UBL_NS = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";
    private static final String CAC_NS = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
    private static final String CBC_NS = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";

    @Override
    public SupplierInvoice parse(byte[] xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document doc = factory.newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xmlContent));

            Element root = doc.getDocumentElement();
            SupplierInvoice invoice = new SupplierInvoice();
            invoice.setIssueDate(parseDate(getText(root, CBC_NS, "IssueDate")));
            invoice.setCurrencyCode(getAttribute(root, CBC_NS, "DocumentCurrencyCode", "listID"));
            if (invoice.getCurrencyCode() == null) {
                invoice.setCurrencyCode(getText(root, CBC_NS, "DocumentCurrencyCode"));
            }

            parseInvoiceId(invoice, root);
            parseSupplier(invoice, root);
            parseTotals(invoice, root);
            parseLines(invoice, root);
            parseHeaderDetraction(invoice, root);

            return invoice;
        } catch (InvoiceValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvoiceValidationException("Error parseando XML UBL 2.1: " + e.getMessage());
        }
    }

    private void parseInvoiceId(SupplierInvoice invoice, Element root) {
        String id = getText(root, CBC_NS, "ID");
        if (id != null && id.contains("-")) {
            String[] parts = id.split("-", 2);
            invoice.setSerialNumber(parts[0]);
            invoice.setInvoiceNumber(parts[1]);
        } else {
            invoice.setInvoiceNumber(id);
        }
    }

    private void parseSupplier(SupplierInvoice invoice, Element root) {
        Element supplierParty = getFirstElement(root, CAC_NS, "AccountingSupplierParty");
        if (supplierParty == null) {
            return;
        }
        Element party = getFirstElement(supplierParty, CAC_NS, "Party");
        if (party == null) {
            return;
        }
        Element partyLegal = getFirstElement(party, CAC_NS, "PartyLegalEntity");
        if (partyLegal != null) {
            invoice.setSupplierName(getText(partyLegal, CBC_NS, "RegistrationName"));
            invoice.setSupplierRuc(getText(partyLegal, CBC_NS, "CompanyID"));
        }
        Element partyId = getFirstElement(party, CAC_NS, "PartyIdentification");
        if (partyId != null && invoice.getSupplierRuc() == null) {
            invoice.setSupplierRuc(getText(partyId, CBC_NS, "ID"));
        }
    }

    private void parseTotals(SupplierInvoice invoice, Element root) {
        Element monetaryTotal = getFirstElement(root, CAC_NS, "LegalMonetaryTotal");
        if (monetaryTotal != null) {
            invoice.setTotalAmount(parseDecimal(getText(monetaryTotal, CBC_NS, "PayableAmount")));
            invoice.setTaxInclusiveAmount(parseDecimal(getText(monetaryTotal, CBC_NS, "TaxInclusiveAmount")));
        }
    }

    private void parseLines(SupplierInvoice invoice, Element root) {
        NodeList lineNodes = root.getElementsByTagNameNS(CAC_NS, "InvoiceLine");
        List<InvoiceLine> lines = new ArrayList<>();
        for (int i = 0; i < lineNodes.getLength(); i++) {
            Element lineEl = (Element) lineNodes.item(i);
            InvoiceLine line = new InvoiceLine();
            line.setLineId(getText(lineEl, CBC_NS, "ID"));
            line.setQuantity(parseDecimal(getText(lineEl, CBC_NS, "InvoicedQuantity")));
            line.setLineExtensionAmount(parseDecimal(getText(lineEl, CBC_NS, "LineExtensionAmount")));

            Element item = getFirstElement(lineEl, CAC_NS, "Item");
            if (item != null) {
                line.setDescription(getText(item, CBC_NS, "Description"));
                Element sellerItem = getFirstElement(item, CAC_NS, "SellersItemIdentification");
                if (sellerItem != null) {
                    line.setProductCode(getText(sellerItem, CBC_NS, "ID"));
                }
            }

            Element price = getFirstElement(lineEl, CAC_NS, "Price");
            if (price != null) {
                line.setUnitPrice(parseDecimal(getText(price, CBC_NS, "PriceAmount")));
            }

            parseLineTaxes(line, lineEl);
            lines.add(line);
        }
        invoice.setLines(lines);
    }

    private void parseLineTaxes(InvoiceLine line, Element lineEl) {
        Element taxTotal = getFirstElement(lineEl, CAC_NS, "TaxTotal");
        if (taxTotal == null) {
            return;
        }
        NodeList subtotals = taxTotal.getElementsByTagNameNS(CAC_NS, "TaxSubtotal");
        List<TaxBreakdown> taxes = new ArrayList<>();
        for (int i = 0; i < subtotals.getLength(); i++) {
            Element subtotal = (Element) subtotals.item(i);
            Element category = getFirstElement(subtotal, CAC_NS, "TaxCategory");
            String sunatCode = null;
            if (category != null) {
                Element scheme = getFirstElement(category, CAC_NS, "TaxScheme");
                if (scheme != null) {
                    sunatCode = getText(scheme, CBC_NS, "ID");
                }
            }
            TaxIdentifier identifier = TaxIdentifier.fromSunatCode(sunatCode);
            if (identifier == null) {
                identifier = TaxIdentifier.IGV;
            }
            taxes.add(new TaxBreakdown(
                    identifier,
                    sunatCode,
                    parseDecimal(getText(subtotal, CBC_NS, "TaxableAmount")),
                    parseDecimal(getText(subtotal, CBC_NS, "TaxAmount")),
                    parseDecimal(getText(subtotal, CBC_NS, "Percent"))
            ));
        }
        line.setTaxes(taxes);
    }

    private void parseHeaderDetraction(SupplierInvoice invoice, Element root) {
        NodeList paymentMeans = root.getElementsByTagNameNS(CAC_NS, "PaymentMeans");
        for (int i = 0; i < paymentMeans.getLength(); i++) {
            Element pm = (Element) paymentMeans.item(i);
            String code = getText(pm, CBC_NS, "PaymentMeansCode");
            if ("Detraccion".equalsIgnoreCase(code) || "999".equals(code)) {
                invoice.setHeaderDetraction(new DetractionInfo(
                        getText(pm, CBC_NS, "PaymentID"),
                        null,
                        parseDecimal(getText(pm, CBC_NS, "PaymentAmount")),
                        null
                ));
                break;
            }
        }
    }

    private Element getFirstElement(Element parent, String ns, String localName) {
        NodeList nodes = parent.getElementsByTagNameNS(ns, localName);
        if (nodes.getLength() == 0) {
            return null;
        }
        Node node = nodes.item(0);
        return node instanceof Element ? (Element) node : null;
    }

    private String getText(Element parent, String ns, String localName) {
        Element el = getFirstElement(parent, ns, localName);
        return el != null ? el.getTextContent().trim() : null;
    }

    private String getAttribute(Element parent, String ns, String localName, String attr) {
        Element el = getFirstElement(parent, ns, localName);
        return el != null ? el.getAttribute(attr) : null;
    }

    private BigDecimal parseDecimal(String value) {
        if (value == null || value.isBlank()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value.trim());
    }

    private LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDate.parse(value.trim());
    }
}
