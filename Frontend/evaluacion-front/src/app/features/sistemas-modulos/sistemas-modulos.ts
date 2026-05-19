// src/app/features/sistemas-modulos/sistemas-modulos.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-sistemas-modulos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div style="max-width: 1000px; margin: 40px auto; padding: 25px; font-family: 'Segoe UI', Roboto, sans-serif; color: #333;">
      
      <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; border-bottom: 2px solid #f4f6f9; padding-bottom: 15px;">
        <div>
          <h2 style="margin: 0; color: #1e293b; font-size: 1.75rem;">Módulos y Sistemas Globales</h2>
          <p style="margin: 5px 0 0 0; color: #64748b; font-size: 0.9rem;">Panel de administración para dar de alta y dar de baja entornos de software de la organización.</p>
        </div>
        <button routerLink="/usuarios" style="padding: 10px 16px; background-color: #64748b; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: 500; display: flex; align-items: center; gap: 8px; transition: background 0.2s;">
          ⬅️ Volver a Usuarios
        </button>
      </div>

      <div style="background: #f8fafc; padding: 20px; border-radius: 8px; margin-bottom: 35px; border: 1px solid #e2e8f0;">
        <h3 style="margin-top: 0; margin-bottom: 15px; color: #334155; font-size: 1.1rem;">⚡ Registrar Nuevo Sistema Maestro</h3>
        <div style="display: flex; gap: 12px; flex-wrap: wrap;">
          <input type="text" [(ngModel)]="nuevoSistema.nombre" placeholder="Nombre (Ej: HELP, RRHH)" style="padding: 10px; border: 1px solid #cbd5e1; border-radius: 6px; flex: 1; min-width: 180px; font-size: 0.9rem;"/>
          <input type="text" [(ngModel)]="nuevoSistema.descripcion" placeholder="Descripción de la plataforma" style="padding: 10px; border: 1px solid #cbd5e1; border-radius: 6px; flex: 2; min-width: 280px; font-size: 0.9rem;"/>
          <button (click)="crearSistema()" style="padding: 10px 20px; background-color: #10b981; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: 600; font-size: 0.9rem;">
            💾 Guardar Sistema
          </button>
        </div>
      </div>

      <div *ngIf="loading" style="text-align: center; padding: 40px; color: #3b82f6; font-weight: 500;">
        🔄 Sincronizando catálogos con la base de datos...
      </div>

      <div *ngIf="!loading" style="background: white; border-radius: 8px; border: 1px solid #e2e8f0; overflow: hidden; box-shadow: 0 1px 3px rgba(0,0,0,0.05);">
        <table style="width: 100%; border-collapse: collapse; text-align: left;">
          <thead>
            <tr style="background: #0f172a; color: white;">
              <th style="padding: 14px 20px; font-size: 0.9rem; font-weight: 600;">ID</th>
              <th style="padding: 14px 20px; font-size: 0.9rem; font-weight: 600;">Identificador Unico</th>
              <th style="padding: 14px 20px; font-size: 0.9rem; font-weight: 600;">Propósito / Descripción</th>
              <th style="padding: 14px 20px; font-size: 0.9rem; font-weight: 600; text-align: center;">Acciones de Control</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let sis of sistemas" style="border-bottom: 1px solid #f1f5f9; transition: background 0.15s;" onmouseover="this.style.backgroundColor='#f8fafc'" onmouseout="this.style.backgroundColor='transparent'">
              <td style="padding: 14px 20px; color: #64748b; font-size: 0.9rem;">#{{sis.id}}</td>
              <td style="padding: 14px 20px; font-weight: 600; color: #0f172a; font-size: 0.95rem;">💻 {{sis.nombre}}</td>
              <td style="padding: 14px 20px; color: #475569; font-size: 0.9rem;">{{sis.descripcion || 'Sin descripción provista.'}}</td>
              <td style="padding: 14px 20px; text-align: center;">
                <button (click)="eliminarSistema(sis.id)" style="padding: 6px 12px; background-color: #ef4444; color: white; border: none; border-radius: 6px; cursor: pointer; font-size: 0.85rem; font-weight: 500; transition: background 0.2s;">
                  🗑️ Eliminar Sistema
                </button>
              </td>
            </tr>
            <tr *ngIf="sistemas.length === 0">
              <td colspan="4" style="text-align: center; padding: 30px; color: #94a3b8; font-style: italic;">No hay sistemas registrados actualmente en la infraestructura.</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  `
})
export class SistemasModulos implements OnInit {
  sistemas: any[] = [];
  nuevoSistema = { nombre: '', descripcion: '', activo: true };
  loading: boolean = false;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.listarSistemas();
  }

  listarSistemas() {
    this.loading = true;
    this.http.get<any[]>('http://localhost:8080/sistemas').subscribe({
      next: (data) => {
        this.sistemas = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al listar sistemas:', err);
        this.loading = false;
      }
    });
  }

  crearSistema() {
    if (!this.nuevoSistema.nombre || this.nuevoSistema.nombre.trim() === '') {
      alert('⚠️ El identificador del sistema es un campo requerido obligatoriamente.');
      return;
    }
    this.http.post('http://localhost:8080/sistemas', this.nuevoSistema).subscribe({
      next: () => {
        this.nuevoSistema = { nombre: '', descripcion: '', activo: true };
        this.listarSistemas();
      },
      error: (err) => alert('Error al registrar sistema: ' + (err.error?.message || err.message))
    });
  }

  eliminarSistema(id: number) {
    if (confirm('⚠️ ATENCIÓN: Al eliminar este sistema se removerán de forma automática todos sus módulos subordinados y los permisos individuales concedidos a usuarios en SQL Server (Borrado en Cascada). ¿Deseas proceder?')) {
      this.http.delete(`http://localhost:8080/sistemas/${id}`).subscribe({
        next: () => this.listarSistemas(),
        error: (err) => alert('Error al procesar la baja del sistema: ' + (err.error?.message || err.message))
      });
    }
  }
}