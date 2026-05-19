// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login';
import { UsuarioList } from './features/usuarios/usuario-list/usuario-list'; // 👈 Tu componente de lista
import { UsuarioDetalle } from './features/usuarios/usuario-detalle/usuario-detalle'; // 👈 Tu componente de detalle

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'usuarios', component: UsuarioList }, // 👈 Ruta activada
  { path: 'usuarios/:id', component: UsuarioDetalle }, // 👈 Ruta dinámica para el detalle
  { path: '**', redirectTo: 'login' }
];