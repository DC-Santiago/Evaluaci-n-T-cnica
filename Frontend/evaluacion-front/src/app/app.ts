// src/app/app.ts
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router'; // 👈 Importante añadir esta línea

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet], // 👈 Asegúrate de incluirlo en este arreglo
  templateUrl: './app.html', // Vinculado a tu archivo app.html
  styleUrl: './app.css'      // Vinculado a tu archivo app.css
})
export class AppComponent {
  title = 'evaluacion-front';
}