import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { IngredientService } from 'src/app/services/ingredient_service/ingredient.service';

@Component({
  selector: 'app-most-popular-combination-of-ingredients',
  templateUrl: './most-popular-combination-of-ingredients.component.html',
  styleUrls: ['./most-popular-combination-of-ingredients.component.css']
})
export class MostPopularCombinationOfIngredientsComponent implements OnInit {
  ingredientName: string = "";
  suggestions: string[] = [];
  ingredientsList: IngredientInterface[] = [];    // LIST WITH ALL INGREDIENTS. INITIALIZED IN ngOnInit() WITH THE CALL TO THE SERVICE
  ingredientDetailed: any = null;   // USED TO SHOW CALORIES

  showList: boolean = false;      // USED TO HANDLE LIST VISIBILITY
  canCloseList: boolean = true;   // USED TO HANDLE LIST VISIBILITY

  constructor(private eRef: ElementRef, private ingredientService: IngredientService) { }

  ngOnInit(): void {
    this.ingredientsList = this.ingredientService.getAllIngredients();
  }


  execute() {
    this.showList = false;
    if(!this.ingredientsList.some(ingredient => ingredient.name === this.ingredientName)) {
      this.suggestions = ["Insert a valid ingredient"];
      return;
    }
    this.ingredientService.mostPopularCombinationOfIngredients(this.ingredientName).subscribe(
      data => {
        this.suggestions = ["No suggestions available"];
        if (data.length > 0)
          this.suggestions = data;
      },
      error => {
        if (error.status === 401) {
          // Gestisci l'errore 401 qui
          alert('Wrong username or password');
        }
      }
    );
  }

  setIngredientDetailed(ingredient: any) {
    this.ingredientDetailed = ingredient;
  }

  newIngredientIsBeingInserted() {
    this.showList=true;
    this.suggestions = [];
  }
  
  activeDropdownIndex: boolean = false;

  toggleDropdown(): void {
    if(this.showList) {
      this.showList = false;
    }
    else{
      setTimeout(() => {
        this.suggestions = [];
        this.ingredientName = "";
      }, 300);
      this.activeDropdownIndex = !this.activeDropdownIndex;
    }
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
        }, 300);
  }

}
