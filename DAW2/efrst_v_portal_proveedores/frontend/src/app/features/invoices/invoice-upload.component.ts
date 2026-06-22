import { Component } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { PortalApiService } from '../../core/services/portal-api.service';
import {
  InvoiceSubmissionResult,
  MATCH_STATUS_LABELS,
  MatchStatus,
} from '../../core/models/portal.models';

@Component({
  selector: 'app-invoice-upload',
  standalone: true,
  imports: [
    FormsModule,
    DecimalPipe,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    MatSnackBarModule,
  ],
  templateUrl: './invoice-upload.component.html',
  styleUrl: './invoice-upload.component.scss',
})
export class InvoiceUploadComponent {
  purchaseOrderNumber = '';
  selectedFile: File | null = null;
  isDragging = false;
  loading = false;
  result: InvoiceSubmissionResult | null = null;
  errorMessage = '';

  readonly statusLabels = MATCH_STATUS_LABELS;

  constructor(
    private readonly portalApi: PortalApiService,
    private readonly snackBar: MatSnackBar
  ) {}

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = true;
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = false;
    const file = event.dataTransfer?.files?.[0];
    if (file) {
      this.setFile(file);
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (file) {
      this.setFile(file);
    }
  }

  private setFile(file: File): void {
    if (!file.name.toLowerCase().endsWith('.xml')) {
      this.snackBar.open('Solo se aceptan archivos XML UBL 2.1', 'Cerrar', { duration: 4000 });
      return;
    }
    this.selectedFile = file;
    this.errorMessage = '';
    this.result = null;
  }

  clearFile(): void {
    this.selectedFile = null;
  }

  submit(): void {
    if (!this.selectedFile) {
      this.errorMessage = 'Seleccione un archivo XML de factura.';
      return;
    }
    if (!this.purchaseOrderNumber.trim()) {
      this.errorMessage = 'Ingrese el nÃºmero de orden de compra (ej. PO00045).';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.result = null;

    this.portalApi.uploadInvoice(this.selectedFile, this.purchaseOrderNumber.trim()).subscribe({
      next: (response) => {
        this.result = response;
        this.loading = false;
        this.snackBar.open('Factura procesada correctamente', 'Cerrar', { duration: 3000 });
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err.error?.message ?? 'Error al subir la factura. Verifique el XML y la OC.';
        this.snackBar.open(this.errorMessage, 'Cerrar', { duration: 5000 });
      },
    });
  }

  statusChipClass(status: MatchStatus): string {
    switch (status) {
      case 'APPROVED':
        return 'status-chip-approved';
      case 'REJECTED':
        return 'status-chip-rejected';
      default:
        return 'status-chip-partial';
    }
  }
}

