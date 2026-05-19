// src/app/core/services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private API = 'http://localhost:8080/auth/login';

  constructor(private http: HttpClient) {}

  // Ajustado para recibir correo y contrasena en lugar de username
  login(correo: string, contrasena: string) {
    return this.http.post<any>(this.API, { correo, contrasena })
      .pipe(
        tap(res => {
          if (res && res.token) {
            localStorage.setItem('token', res.token);
          }
        })
      );
  }

 getToken(): string | null {
  
  if (typeof window !== 'undefined' && window.localStorage) {
    return localStorage.getItem('token'); 
  }
  return null;
}

  logout() {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}