import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { IngredientService } from 'src/app/services/ingredient_service/ingredient.service';
import { FilterIngredientPipe } from 'src/app/pipes/filter-ingredient.pipe';

@Component({
  selector: 'app-find-users-by-ingredient-usage',
  templateUrl: './find-users-by-ingredient-usage.component.html',
  styleUrls: ['./find-users-by-ingredient-usage.component.css']
})
export class FindUsersByIngredientUsageComponent implements OnInit {
  ingredientsList: IngredientInterface[] = [];    // LIST WITH ALL INGREDIENTS. INITIALIZED IN ngOnInit() WITH THE CALL TO THE SERVICE
  ingredientName: string = "";    // USED TO SHOW THE NAME OF THE INGREDIENT IN THE INPUT FIELD
  ingredientDetailed: any = null;   // USED TO SHOW CALORIES

  showList: boolean = false;      // USED TO HANDLE LIST VISIBILITY
  canCloseList: boolean = true;   // USED TO HANDLE LIST VISIBILITY

  constructor(private eRef: ElementRef, private ingredientService: IngredientService) { }

  ngOnInit(): void {
    this.ingredientsList = this.ingredientService.getAllIngredients();
  }

  doQuery() {
    this.showList = false;
    if(!this.ingredientsList.some(ingredient => ingredient.name === this.ingredientName)) {
      alert("Ingredient not found");
    }
    // HERE WE SHOULD CALL ONEXECUTE() TO RETURN THE OUTPUT TO REGISTERED USER FEED COMPONENT
  }

  setIngredientDetailed(ingredient: any) {
    this.ingredientDetailed = ingredient;
  }

  /* USED TO HANDLE WINDOW VISIBILITY ON BUTTON CLICK */

  activeDropdownIndex: boolean = false;

  toggleDropdown(): void {
    if(this.showList) {
      this.showList = false;
    }
    else
      this.activeDropdownIndex = !this.activeDropdownIndex;
  }

  isActiveDropdown(): boolean {
    return this.activeDropdownIndex;
  }

  @HostListener('document:click', ['$event'])
  clickout(event:any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      if(this.showList) {
        this.showList = false;
      }
      else if (this.isActiveDropdown() && this.canCloseList) {
        this.toggleDropdown();
      }
    }
  }

  setIngredient(ingredientName: string) {
    this.ingredientName = ingredientName;
    this.showList = false;
    this.ingredientDetailed = null;
    this.canCloseList = false;
        setTimeout(() => {
          this.canCloseList = true;
        }, 1000);
  }

  /* USED TO RETURN OUTPUT TO REIGSTERED USER FEED COMPONENT */

  @Output() findUsersByIngredientUsage = new EventEmitter<any>();

  onExecute() {
    // Prepara i dati da inviare
    const dataToEmit = {
      ingredientName: this.ingredientName
    };
    // Emetti l'evento con i dati
    this.findUsersByIngredientUsage.emit(dataToEmit);
  }

}
