import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { IngredientService } from 'src/app/services/ingredient_service/ingredient.service';
import { FilterIngredientPipe } from 'src/app/pipes/filter-ingredient.pipe';
import { RegisteredUserService } from 'src/app/services/registered_user_service/registered-user.service';
import { RegisteredUserDTOInterface } from 'src/app/models/registered-user-dto.model';

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

  suggestions: RegisteredUserDTOInterface[] = [];     // USED TO SHOW SUGGESTIONS

  constructor(private eRef: ElementRef, private ingredientService: IngredientService, private registeredUserService: RegisteredUserService) { }

  ngOnInit(): void {
    this.ingredientsList = this.ingredientService.getAllIngredients();
  }

  execute() {
    console.log(this.ingredientName);
    this.showList = false;
    if(!this.ingredientsList.some(ingredient => ingredient.name === this.ingredientName)) {
      this.suggestions = [{id: "", username: "Insert a valid ingredient"}];
      return;
    }
    this.registeredUserService.findUsersByIngredientUsage(this.ingredientName).subscribe(
      data => {
        this.suggestions = [{id: "", username: "No suggestions available"}];
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

  newIngredientIsBeingInserted() {
    this.showList=true;
    this.suggestions = [];
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
    this.canCloseList = false;
    this.ingredientName = ingredientName;
    this.showList = false;
    this.ingredientDetailed = null;
    setTimeout(() => {
      this.canCloseList = true;
    }, 1000);
  }

  /* USED TO RETURN OUTPUT TO REIGSTERED USER FEED COMPONENT */

  // @Output() findUsersByIngredientUsage = new EventEmitter<any>();

  // onExecute() {
  //   // Prepara i dati da inviare
  //   const dataToEmit = {
  //     ingredientName: this.ingredientName
  //   };
  //   // Emetti l'evento con i dati
  //   this.findUsersByIngredientUsage.emit(dataToEmit);
  // }

}
