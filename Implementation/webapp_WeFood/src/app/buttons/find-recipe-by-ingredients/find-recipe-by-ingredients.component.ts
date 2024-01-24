import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { IngredientService } from 'src/app/services/ingredient_service/ingredient.service';
import { PostService } from 'src/app/services/post_service/post.service';

@Component({
  selector: 'app-find-recipe-by-ingredients',
  templateUrl: './find-recipe-by-ingredients.component.html',
  styleUrls: ['./find-recipe-by-ingredients.component.css']
})
export class FindRecipeByIngredientsComponent implements OnInit {
  hours_var: number = 87600;
  limit_var: number = 30;

  ingredients_chosen: string[] = [];
  ingredientName: string = "";
  ingredientsList: IngredientInterface[] = [];    // LIST WITH ALL INGREDIENTS. INITIALIZED IN ngOnInit() WITH THE CALL TO THE SERVICE
  ingredientsRemaining: IngredientInterface[] = [];    // LIST WITH ALL INGREDIENTS. INITIALIZED IN ngOnInit() WITH THE CALL TO THE SERVICE
  ingredientDetailed: any = null;   // USED TO SHOW CALORIES

  showList: boolean = false;      // USED TO HANDLE LIST VISIBILITY
  canCloseList: boolean = true;   // USED TO HANDLE LIST VISIBILITY

  setIngredientDetailed(ingredient: any) {
    this.ingredientDetailed = ingredient;
  }
  newIngredientIsBeingInserted() {
    this.showList=true;
  }


  constructor(private eRef: ElementRef, private ingredientService: IngredientService, private postService: PostService) { }

  ngOnInit(): void {
    this.ingredientsList = this.ingredientService.getAllIngredients();
    this.ingredientsRemaining = this.ingredientsList;
  }

  activeDropdownIndex: boolean = false;

  toggleDropdown(): void {
    if(this.showList) {
      this.showList = false;
    }
    else{
      setTimeout(() => {
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
      else if (this.removingIngredient){
        return;
      }
      else if (this.isActiveDropdown() && this.canCloseList) {
        this.toggleDropdown();
        setTimeout(() => {
          this.ingredientsRemaining = this.ingredientsList;
          this.ingredients_chosen = [];
        }, 200);
      }
    }
  }

  addIngredient(ingredientName: string) {
    this.ingredients_chosen.push(ingredientName);
    this.ingredientsRemaining = this.ingredientsRemaining.filter(ingredient => ingredient.name !== ingredientName);
    this.ingredientName = "";
    this.showList = false;
    this.ingredientDetailed = null;
    this.canCloseList = false;
    setTimeout(() => {
      this.canCloseList = true;
    }, 300);
  }

  removingIngredient: boolean = false;

  removeIngredient(ingredientName: string) {
    this.removingIngredient = true;

    this.ingredients_chosen = this.ingredients_chosen.filter(ingredient => ingredient !== ingredientName);
    this.ingredientsRemaining.push(this.ingredientsList.find(ingredient => ingredient.name === ingredientName)!);
    this.ingredientDetailed = null;

    setTimeout(() => {
      this.removingIngredient = false;
    }, 300);
  }

  @Output() sendPosts = new EventEmitter<any>();
  @Output() loadPosts = new EventEmitter<any>();
  
  onExecute() {
    // Prepara i dati da inviare
    this.loadPosts.emit();
    this.postService.findRecipeByIngredients(this.ingredients_chosen).subscribe(
      (response) => {
        const dataToEmit = {
          posts: response
        };
        // Emetti l'evento con i dati
        this.sendPosts.emit(dataToEmit);
          },
      (error) => {
        console.log(error);
        this.loadPosts.emit();
      }
    );
  }
}
