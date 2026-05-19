// src/app/app.config.ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
// 🔹 Importamos withFetch para resolver el aviso de compatibilidad con SSR
import { provideHttpClient, withInterceptorsFromDi, withFetch, HTTP_INTERCEPTORS } from '@angular/common/http';

import { routes } from './app.routes';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    
    // 🚀 Habilitamos HttpClient configurado tanto para interceptores clásicos como para usar la API Fetch nativa
    provideHttpClient(
      withInterceptorsFromDi(),
      withFetch() // 🔹 Agregado aquí para optimizar las peticiones síncronas en el servidor y cliente
    ),
    
    // Registramos tu interceptor para que inyecte el Bearer token en cada petición
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ]
};