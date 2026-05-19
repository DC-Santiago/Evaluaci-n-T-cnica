// src/app/features/auth/login/login.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service'; // Subimos 3 niveles para llegar a core

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html', // Coincide con tu archivo login.html
  styleUrl: './login.css'       // Coincide con tu archivo login.css
})
export class LoginComponent {
  correo: string = '';
  contrasena: string = '';
  
  loading: boolean = false;
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    if (!this.correo || !this.contrasena) {
      this.errorMessage = 'Por favor, completa todos los campos.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.authService.login(this.correo, this.contrasena).subscribe({
      next: (response) => {
        this.loading = false;
        // Cuando el backend nos regrese el token con éxito, mandamos al usuario al listado
        this.router.navigate(['/usuarios']);
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = 'Credenciales inválidas o error de conexión con el servidor.';
        console.error(err);
      }
    });
  }
}