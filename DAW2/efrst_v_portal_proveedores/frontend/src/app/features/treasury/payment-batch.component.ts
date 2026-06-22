import { Component, OnInit } from '@angular/core';
import { DecimalPipe, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { SelectionModel } from '@angular/cdk/collections';
import { PortalApiService } from '../../core/services/portal-api.service';
import {
  OpenVendorBill,
  PaymentBatchCriteria,
  PaymentBatchResponse,
} from '../../core/models/portal.models';

@Component({
  selector: 'app-payment-batch',
  standalone: true,
  imports: [
    DecimalPipe,
    DatePipe,
    FormsModule,
    MatCardModule,
    MatTableModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
  ],
  templateUrl: './payment-batch.component.html',
  styleUrl: './payment-batch.component.scss',
})
export class PaymentBatchComponent implements OnInit {
  readonly displayedColumns = [
    'select',
    'reference',
    'supplierName',
    'supplierRuc',
    'amountDue',
    'currencyCode',
    'dueDate',
  ];

  bills: OpenVendorBill[] = [];
  selection = new SelectionModel<OpenVendorBill>(true, []);
  loading = false;
  generating = false;
  errorMessage = '';

  paymentDate = this.todayIso();
  currencyCode = 'PEN';
  supplierRucFilter = '';

  lastBatch: PaymentBatchResponse | null = null;

  readonly currencyOptions = ['PEN', 'USD'];

  constructor(
    private readonly portalApi: PortalApiService,
    private readonly snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadOpenBills();
  }

  loadOpenBills(): void {
    this.loading = true;
    this.errorMessage = '';
    this.selection.clear();
    this.lastBatch = null;

    const criteria: PaymentBatchCriteria = {
      currencyCode: this.currencyCode,
      dueDateTo: this.paymentDate,
    };

    this.portalApi.getOpenBills(criteria).subscribe({
      next: (bills: OpenVendorBill[]) => {
        const rucFilter = this.supplierRucFilter.trim();
        this.bills = rucFilter
          ? bills.filter((bill) => bill.supplierRuc.includes(rucFilter))
          : bills;
        this.loading = false;
      },
      error: (err: { error?: { error?: string; message?: string } }) => {
        this.loading = false;
        this.errorMessage =
          err.error?.error ?? err.error?.message ?? 'No se pudieron cargar las facturas abiertas.';
        this.snackBar.open(this.errorMessage, 'Cerrar', { duration: 5000 });
      },
    });
  }

  isAllSelected(): boolean {
    return this.bills.length > 0 && this.selection.selected.length === this.bills.length;
  }

  toggleAllRows(): void {
    if (this.isAllSelected()) {
      this.selection.clear();
    } else {
      this.selection.select(...this.bills);
    }
  }

  selectedTotal(): number {
    return this.selection.selected.reduce((sum, bill) => sum + bill.amountDue, 0);
  }

  generateBatch(): void {
    if (this.selection.selected.length === 0) {
      this.snackBar.open('Seleccione al menos una factura', 'Cerrar', { duration: 3000 });
      return;
    }

    this.generating = true;
    this.lastBatch = null;

    this.portalApi
      .createPaymentBatch({
        paymentDate: this.paymentDate,
        currencyCode: this.currencyCode,
        invoiceIds: this.selection.selected.map((b) => b.erpInvoiceId),
      })
      .subscribe({
        next: (batch: PaymentBatchResponse) => {
          this.lastBatch = batch;
          this.generating = false;
          this.snackBar.open(`Lote ${batch.batchId} generado correctamente`, 'Cerrar', {
            duration: 4000,
          });
        },
        error: (err: { error?: { message?: string } }) => {
          this.generating = false;
          const message = err.error?.message ?? 'Error al generar el lote de pago.';
          this.snackBar.open(message, 'Cerrar', { duration: 5000 });
        },
      });
  }

  downloadBatch(): void {
    if (!this.lastBatch) {
      return;
    }

    this.portalApi.downloadBatchFile(this.lastBatch.batchId).subscribe({
      next: (blob: Blob) => {
        const url = URL.createObjectURL(blob);
        const anchor = document.createElement('a');
        anchor.href = url;
        anchor.download = this.lastBatch!.fileName || `LOTE_${this.lastBatch!.batchId}.txt`;
        anchor.click();
        URL.revokeObjectURL(url);
        this.snackBar.open('Archivo TXT descargado', 'Cerrar', { duration: 3000 });
      },
      error: () => {
        this.snackBar.open('Error al descargar el archivo TXT', 'Cerrar', { duration: 5000 });
      },
    });
  }

  private todayIso(): string {
    return new Date().toISOString().slice(0, 10);
  }
}



