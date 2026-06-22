import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./features/home/home.component').then((m) => m.HomeComponent),
  },
  {
    path: 'invoices',
    loadComponent: () =>
      import('./features/invoices/invoice-upload.component').then(
        (m) => m.InvoiceUploadComponent
      ),
  },
  {
    path: 'matching/:id',
    loadComponent: () =>
      import('./features/matching/match-detail.component').then(
        (m) => m.MatchDetailComponent
      ),
  },
  {
    path: 'treasury',
    loadComponent: () =>
      import('./features/treasury/payment-batch.component').then(
        (m) => m.PaymentBatchComponent
      ),
  },
  { path: '**', redirectTo: '' },
];
