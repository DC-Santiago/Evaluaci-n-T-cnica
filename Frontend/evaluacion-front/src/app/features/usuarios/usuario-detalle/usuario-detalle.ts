// src/app/features/usuarios/usuario-detalle/usuario-detalle.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-usuario-detalle',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './usuario-detalle.html',
  styleUrl: './usuario-detalle.css'
})
export class UsuarioDetalle implements OnInit {
  usuarioId!: number;
  usuario: any = null;
  modulos: any[] = [];
  permisosUsuario: any[] = [];
  
  // 🔍 Filtro amarrado al buscador dinámico de la vista
  filtro: string = '';
  
  loading: boolean = true;
  error: boolean = false;

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.usuarioId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.usuarioId) {
      this.cargarDatosIniciales();
    } else {
      this.error = true;
      this.loading = false;
    }
  }

  async cargarDatosIniciales() {
    this.loading = true;
    this.error = false;
    try {
      this.modulos = await this.http.get<any[]>('http://localhost:8080/modulos').toPromise() || [];
      const usuariosLista = await this.http.get<any[]>('http://localhost:8080/usuarios').toPromise() || [];
      this.usuario = usuariosLista.find((u: any) => u.id === this.usuarioId);
      this.permisosUsuario = await this.http.get<any[]>(`http://localhost:8080/api/permisos/usuario/${this.usuarioId}`).toPromise() || [];
      this.loading = false;
    } catch (err) {
      console.error('Error crítico al cargar la matriz de permisos:', err);
      this.error = true;
      this.loading = false;
    }
  }

  modulosFiltrados() {
    if (!this.filtro || this.filtro.trim() === '') {
      return this.modulos;
    }
    const txt = this.filtro.toLowerCase();
    return this.modulos.filter(m => 
      m.nombre?.toLowerCase().includes(txt) || 
      m.sistema?.nombre?.toLowerCase().includes(txt)
    );
  }

  obtenerPermisoDeModulo(moduloId: number) {
    const mod = this.modulos.find(m => m.id === moduloId);
    if (!mod || !this.permisosUsuario) return null;
    
    return this.permisosUsuario.find(p => 
      p.modulo === mod.nombre && p.sistema === mod.sistema?.nombre
    );
  }

  // 🎯 MODIFICADO: Solución al delay de estados asíncronos y regla de negocio unificada
  alternarPermiso(moduloId: number, tipo: 'leer' | 'escribir', evento: any) {
    const checked = evento.target.checked;
    let permisoExistente = this.obtenerPermisoDeModulo(moduloId);

    // 1. Calcular los nuevos estados lógicos requeridos
    let nuevoPuedeLeer = permisoExistente ? permisoExistente.puedeLeer : false;
    let nuevoPuedeEscribir = permisoExistente ? permisoExistente.puedeEscribir : false;

    if (tipo === 'leer') {
      nuevoPuedeLeer = checked;
      if (!checked) {
        nuevoPuedeEscribir = false; // Remueve escritura en cascada si se apaga la lectura
      }
    } else if (tipo === 'escribir') {
      nuevoPuedeEscribir = checked;
      if (checked) {
        nuevoPuedeLeer = true; // Forzar lectura si se activa escritura
      }
    }

    // 2. 🚀 Sincronización Local Inmediata (Evita el delay de los 2 clics)
    if (permisoExistente) {
      permisoExistente.puedeLeer = nuevoPuedeLeer;
      permisoExistente.puedeEscribir = nuevoPuedeEscribir;
    } else {
      // Si el registro no existía en el array, creamos un cascarón reactivo temporal
      const mod = this.modulos.find(m => m.id === moduloId);
      const nuevoPermisoTmp = {
        id: 0, // ID temporal en lo que responde la base de datos
        modulo: mod?.nombre,
        sistema: mod?.sistema?.nombre,
        puedeLeer: nuevoPuedeLeer,
        puedeEscribir: nuevoPuedeEscribir
      };
      this.permisosUsuario.push(nuevoPermisoTmp);
      permisoExistente = nuevoPermisoTmp;
    }

    // 3. Persistencia en Backend en segundo plano
    if (!nuevoPuedeLeer && !nuevoPuedeEscribir) {
      // Caso DELETE: Ambos en false implica remover asignación
      this.http.delete(`http://localhost:8080/api/permisos/${permisoExistente.id}`).subscribe({
        next: () => this.recargarPermisos(),
        error: (err: any) => {
          console.error('Error al revocar permiso:', err);
          this.recargarPermisos(); // Revierte al estado real si falla
        }
      });
    } else if (permisoExistente.id !== 0) {
      // Caso PUT: Actualizar banderas existentes
      const body = {
        usuarioId: this.usuarioId,
        moduloId: moduloId,
        puedeLeer: nuevoPuedeLeer,
        puedeEscribir: nuevoPuedeEscribir
      };
      this.http.put(`http://localhost:8080/api/permisos/${permisoExistente.id}`, body).subscribe({
        next: () => this.recargarPermisos(),
        error: (err: any) => {
          alert('Error al actualizar permiso: ' + (err.error?.message || err.message));
          this.recargarPermisos();
        }
      });
    } else {
      // Caso POST: Es una inserción nueva
      const body = {
        usuarioId: this.usuarioId,
        moduloId: moduloId,
        puedeLeer: nuevoPuedeLeer,
        puedeEscribir: nuevoPuedeEscribir
      };
      this.http.post('http://localhost:8080/api/permisos', body).subscribe({
        next: () => this.recargarPermisos(),
        error: (err: any) => {
          alert('Error al crear nuevo acceso: ' + (err.error?.message || err.message));
          this.recargarPermisos();
        }
      });
    }
  }

  recargarPermisos() {
    this.http.get<any[]>(`http://localhost:8080/api/permisos/usuario/${this.usuarioId}`).subscribe({
      next: (data) => this.permisosUsuario = data,
      error: (err) => console.error('Error al refrescar permisos:', err)
    });
  }
}