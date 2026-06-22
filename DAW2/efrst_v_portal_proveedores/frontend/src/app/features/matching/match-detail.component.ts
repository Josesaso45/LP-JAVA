import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { DecimalPipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PortalApiService } from '../../core/services/portal-api.service';
import {
  DISCREPANCY_TYPE_LABELS,
  MATCH_STATUS_LABELS,
  MatchDiscrepancy,
  MatchStatus,
  MatchStatusResponse,
} from '../../core/models/portal.models';

@Component({
  selector: 'app-match-detail',
  standalone: true,
  imports: [
    DecimalPipe,
    RouterLink,
    MatCardModule,
    MatTableModule,
    MatChipsModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './match-detail.component.html',
  styleUrl: './match-detail.component.scss',
})
export class MatchDetailComponent implements OnInit {
  invoiceId = '';
  loading = true;
  errorMessage = '';
  data: MatchStatusResponse | null = null;

  readonly displayedColumns = ['type', 'lineReference', 'expectedValue', 'actualValue', 'message'];
  readonly statusLabels = MATCH_STATUS_LABELS;
  readonly discrepancyLabels = DISCREPANCY_TYPE_LABELS;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly portalApi: PortalApiService
  ) {}

  ngOnInit(): void {
    this.invoiceId = this.route.snapshot.paramMap.get('id') ?? '';
    if (!this.invoiceId) {
      this.errorMessage = 'ID de factura no vÃ¡lido.';
      this.loading = false;
      return;
    }
    this.loadMatchStatus();
  }

  loadMatchStatus(): void {
    this.loading = true;
    this.errorMessage = '';

    this.portalApi.getMatchStatus(this.invoiceId).subscribe({
      next: (response) => {
        this.data = response;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err.error?.message ?? 'No se pudo obtener el estado de matching.';
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

  discrepancyLabel(row: MatchDiscrepancy): string {
    return this.discrepancyLabels[row.type];
  }
}

