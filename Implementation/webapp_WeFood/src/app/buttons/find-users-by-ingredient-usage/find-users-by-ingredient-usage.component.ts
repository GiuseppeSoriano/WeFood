import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-find-users-by-ingredient-usage',
  templateUrl: './find-users-by-ingredient-usage.component.html',
  styleUrls: ['./find-users-by-ingredient-usage.component.css']
})
export class FindUsersByIngredientUsageComponent implements OnInit {
  ingredientName: string = "";

  constructor(private eRef: ElementRef) { }

  ngOnInit(): void {
  }

  @Output() findUsersByIngredientUsage = new EventEmitter<any>();

  onExecute() {
    // Prepara i dati da inviare
    const dataToEmit = {
      // I tuoi dati qui
    };

    // Emetti l'evento con i dati
    this.findUsersByIngredientUsage.emit(dataToEmit);
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
