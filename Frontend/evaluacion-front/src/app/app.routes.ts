// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login';
import { UsuarioList } from './features/usuarios/usuario-list/usuario-list'; // 👈 Tu componente de lista
import { UsuarioDetalle } from './features/usuarios/usuario-detalle/usuario-detalle'; // 👈 Tu componente de detalle
import { SistemasModulos } from './features/sistemas-modulos/sistemas-modulos';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'usuarios', component: UsuarioList }, 
  { path: 'usuarios/:id', component: UsuarioDetalle }, 
  { path: '**', redirectTo: 'login' },
  { path: 'administracion/sistemas', component: SistemasModulos },
  { path: '**', redirectTo: 'login' }
];