import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-most-popular-combination-of-ingredients',
  templateUrl: './most-popular-combination-of-ingredients.component.html',
  styleUrls: ['./most-popular-combination-of-ingredients.component.css']
})
export class MostPopularCombinationOfIngredientsComponent implements OnInit {
  ingredientName: string = "";

  constructor(private eRef: ElementRef) { }

  ngOnInit(): void {
  }

  @Output() mostPopularCombinationOfIngredients = new EventEmitter<any>();

  onExecute() {
    // Prepara i dati da inviare
    const dataToEmit = {
      // I tuoi dati qui
    };

    // Emetti l'evento con i dati
    this.mostPopularCombinationOfIngredients.emit(dataToEmit);
  }

  activeDropdownIndex: boolean = false;

  toggleDropdown(): void {
    this.activeDropdownIndex = !this.activeDropdownIndex;
  }

  isActiveDropdown(): boolean {
    return this.activeDropdownIndex;
  }

  @HostListener('document:click', ['$event'])
  clickout(event:any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      if (this.isActiveDropdown()) {
        this.toggleDropdown();
      }
    }
  }

}
