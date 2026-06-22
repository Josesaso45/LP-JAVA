import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  GenerateBatchRequest,
  InvoiceSubmissionResult,
  MatchStatusResponse,
  OpenVendorBill,
  PaymentBatchCriteria,
  PaymentBatchResponse,
} from '../models/portal.models';

@Injectable({ providedIn: 'root' })
export class PortalApiService {
  private readonly baseUrl = environment.apiUrl;

  constructor(private readonly http: HttpClient) {}

  uploadInvoice(file: File, purchaseOrderNumber: string): Observable<InvoiceSubmissionResult> {
    const formData = new FormData();
    formData.append('xml', file);
    formData.append('purchaseOrderNumber', purchaseOrderNumber);
    return this.http.post<InvoiceSubmissionResult>(`${this.baseUrl}/invoices/upload`, formData);
  }

  getMatchStatus(invoiceId: string): Observable<MatchStatusResponse> {
    return this.http.get<MatchStatusResponse>(`${this.baseUrl}/invoices/${invoiceId}/match-status`);
  }

  getOpenBills(criteria?: PaymentBatchCriteria): Observable<OpenVendorBill[]> {
    let params = new HttpParams();
    if (criteria?.currencyCode) {
      params = params.set('currencyCode', criteria.currencyCode);
    }
    if (criteria?.dueDateFrom) {
      params = params.set('dueDateFrom', criteria.dueDateFrom);
    }
    if (criteria?.dueDateTo) {
      params = params.set('dueDateTo', criteria.dueDateTo);
    }
    return this.http.get<OpenVendorBill[]>(`${this.baseUrl}/treasury/open-bills`, { params });
  }

  createPaymentBatch(request: GenerateBatchRequest): Observable<PaymentBatchResponse> {
    return this.http.post<PaymentBatchResponse>(`${this.baseUrl}/treasury/batches`, request);
  }

  downloadBatchFile(batchId: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/treasury/batches/${batchId}/download`, {
      responseType: 'blob',
    });
  }
}
