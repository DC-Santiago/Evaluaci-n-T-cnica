// src/app/features/usuarios/usuario-list/usuario-list.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [CommonModule, FormsModule], // Importantes para los filtros y las directivas
  templateUrl: './usuario-list.html',
  styleUrl: './usuario-list.css',
})
export class UsuarioList implements OnInit {
  usuarios: any[] = [];
  filtro: string = ''; // Variable amarrada a tu buscador
  
  // Manejo visual de estados solicitado en los requerimientos
  loading: boolean = false;
  error: boolean = false;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.loading = true;
    this.error = false;

    // Consumimos tu endpoint de Spring Boot 
    this.http.get<any[]>('http://localhost:8080/usuarios').subscribe({
      next: (data) => {
        this.usuarios = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.error = true;
        this.loading = false;
      }
    });
  }

  // Buscador que filtra en tiempo real por Nombre o Correo
  usuariosFiltrados() {
    return this.usuarios.filter(u => 
      u.nombre?.toLowerCase().includes(this.filtro.toLowerCase()) ||
      u.correo?.toLowerCase().includes(this.filtro.toLowerCase())
    );
  }

  verDetalle(id: number) {
    this.router.navigate(['/usuarios', id]);
  }
}