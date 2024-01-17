import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  isLoading = true;

  constructor() {
    // Simula un processo di caricamento (es: caricamento dati)
    setTimeout(() => {
      this.isLoading = false;
    }, 3000); // per esempio, attende 3 secondi
  }
}
