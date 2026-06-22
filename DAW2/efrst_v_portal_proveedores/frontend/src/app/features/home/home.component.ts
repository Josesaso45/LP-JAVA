import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  readonly modules = [
    {
      title: 'Carga de facturas',
      description: 'Sube XML UBL 2.1 y vincula con la orden de compra para validación tributaria y logística.',
      icon: 'upload_file',
      route: '/invoices',
      color: '#3949ab',
    },
    {
      title: 'Three-way matching',
      description: 'Consulta el estado de conciliación entre factura, orden de compra y recepción.',
      icon: 'fact_check',
      route: '/invoices',
      color: '#00897b',
    },
    {
      title: 'Tesorería',
      description: 'Selecciona facturas abiertas y genera lotes de pago Telecrédito BCP.',
      icon: 'account_balance',
      route: '/treasury',
      color: '#6a1b9a',
    },
  ];
}
