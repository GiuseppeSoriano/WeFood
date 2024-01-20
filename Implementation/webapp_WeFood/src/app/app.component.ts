import { Component } from '@angular/core';
import { IngredientService } from './services/ingredient_service/ingredient.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  isLoading = true;

  constructor(private ingredientService: IngredientService) {
    // Simula un processo di caricamento (es: caricamento dati)
    setTimeout(() => {
      this.isLoading = false;
    }, 3000); // per esempio, attende 3 secondi
  }

  ngOnInit(): void {
    this.ingredientService.initializeIngredients();
  }
}
