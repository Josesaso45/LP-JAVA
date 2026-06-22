export type MatchStatus = 'APPROVED' | 'REJECTED' | 'PARTIAL';

export type DiscrepancyType =
  | 'PO_NOT_FOUND'
  | 'PO_INVALID_STATE'
  | 'INVOICE_POLICY_MISMATCH'
  | 'SUPPLIER_MISMATCH'
  | 'QUANTITY_EXCEEDED'
  | 'PRICE_MISMATCH'
  | 'PRODUCT_NOT_FOUND';

export type TaxIdentifier = 'IGV' | 'RETENCION' | 'DETRACCION';

export interface MatchDiscrepancy {
  type: DiscrepancyType;
  lineReference: string;
  expectedValue: string;
  actualValue: string;
  message: string;
}

export interface ThreeWayMatchResult {
  status: MatchStatus;
  discrepancies: MatchDiscrepancy[];
}

export interface InvoiceLine {
  lineNumber: string;
  productCode: string;
  description: string;
  quantity: number;
  unitPrice: number;
  lineExtensionAmount: number;
  taxes: TaxBreakdown[];
}

export interface TaxBreakdown {
  taxType: TaxIdentifier;
  sunatTaxCode: string;
  taxableAmount: number;
  taxAmount: number;
  percent: number;
}

export interface DetractionInfo {
  sunatProductCode: string;
  percent: number;
  amount: number;
  paymentMeansCode: string;
}

export interface SupplierInvoice {
  id: string;
  serialNumber: string;
  invoiceNumber: string;
  supplierRuc: string;
  supplierName: string;
  currencyCode: string;
  issueDate: string;
  purchaseOrderNumber: string;
  totalAmount: number;
  taxExclusiveAmount: number;
  taxInclusiveAmount: number;
  lines: InvoiceLine[];
  documentTaxes: TaxBreakdown[];
  documentDetraction?: DetractionInfo;
  documentTypeCode: string;
}

export interface InvoiceSubmissionResult {
  invoiceId: string;
  invoiceNumber: string;
  supplierRuc: string;
  matchResult: ThreeWayMatchResult;
  erpInvoiceId?: number;
  submitted: boolean;
  message?: string;
}

export interface MatchStatusResponse {
  invoiceId: string;
  invoiceNumber: string;
  purchaseOrderNumber: string;
  supplierRuc: string;
  supplierName: string;
  matchResult: ThreeWayMatchResult;
  erpInvoiceId?: number;
}

export interface OpenVendorBill {
  erpInvoiceId: number;
  reference: string;
  supplierRuc: string;
  supplierName: string;
  beneficiaryAccount: string;
  beneficiaryCci: string;
  amountDue: number;
  currencyCode: string;
  dueDate: string;
}

export interface PaymentBatchCriteria {
  currencyCode?: string;
  dueDateFrom?: string;
  dueDateTo?: string;
}

export interface PaymentBatchLine {
  erpInvoiceId: number;
  reference: string;
  supplierRuc: string;
  supplierName: string;
  beneficiaryAccount: string;
  beneficiaryCci: string;
  amount: number;
  currencyCode: string;
}

export interface GenerateBatchRequest {
  paymentDate: string;
  currencyCode: string;
  invoiceIds: number[];
}

export interface PaymentBatchResponse {
  batchId: string;
  paymentDate: string;
  currencyCode: string;
  originAccount: string;
  totalAmount: number;
  lineCount: number;
  fileName: string;
}

export interface PaymentBatch {
  id: string;
  paymentDate: string;
  currencyCode: string;
  originAccount: string;
  totalAmount: number;
  lines: PaymentBatchLine[];
}

export const DISCREPANCY_TYPE_LABELS: Record<DiscrepancyType, string> = {
  PO_NOT_FOUND: 'Orden de compra no encontrada',
  PO_INVALID_STATE: 'Estado de OC invÃ¡lido',
  INVOICE_POLICY_MISMATCH: 'PolÃ­tica de facturaciÃ³n incompatible',
  SUPPLIER_MISMATCH: 'Proveedor no coincide',
  QUANTITY_EXCEEDED: 'Cantidad excedida',
  PRICE_MISMATCH: 'Precio no coincide',
  PRODUCT_NOT_FOUND: 'Producto no encontrado',
};

export const MATCH_STATUS_LABELS: Record<MatchStatus, string> = {
  APPROVED: 'Aprobado',
  REJECTED: 'Rechazado',
  PARTIAL: 'Parcial',
};

