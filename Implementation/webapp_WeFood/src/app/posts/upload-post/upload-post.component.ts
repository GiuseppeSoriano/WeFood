// upload-post.component.ts
import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { PostDTO } from 'src/app/models/post-dto.model';
import { Post, PostInterface } from 'src/app/models/post.model';
import { Recipe, RecipeInterface } from 'src/app/models/recipe.model';
import { IngredientService } from 'src/app/services/ingredient_service/ingredient.service';
import { PostService } from 'src/app/services/post_service/post.service';
import { RegisteredUserService } from 'src/app/services/registered_user_service/registered-user.service';
//import { FileUploader, FileItem } from 'ng2-file-upload';

/*
INFO IN FONDO ALLA PAGINA
*/


@Component({
  selector: 'app-upload-post',
  templateUrl: './upload-post.component.html',
  styleUrls: ['./upload-post.component.css']
})
export class UploadPostComponent implements OnInit {

  @Output() closePost: EventEmitter<void> = new EventEmitter();

  post: PostInterface = new Post();
  // recipe: RecipeInterface = new Recipe();

  stepBeingInserted: string = "";

  ingredients_chosen: Map<string, number> = new Map<string, number>();
  ingredientName: string = "";
  ingredientsList: IngredientInterface[] = [];    // LIST WITH ALL INGREDIENTS. INITIALIZED IN ngOnInit() WITH THE CALL TO THE SERVICE
  ingredientsRemaining: IngredientInterface[] = [];    // LIST WITH ALL INGREDIENTS. INITIALIZED IN ngOnInit() WITH THE CALL TO THE SERVICE
  ingredientDetailed: any = null;   // USED TO SHOW CALORIES
  quantity: number = 0;

  showList: boolean = false;      // USED TO HANDLE LIST VISIBILITY
  canCloseList: boolean = true;   // USED TO HANDLE LIST VISIBILITY

  setIngredientDetailed(ingredient: any) {
    this.ingredientDetailed = ingredient;
  }

  addingIngredients: boolean = false;
  addingSteps: boolean = false;

  toogleSteps() {
    this.addingSteps = !this.addingSteps;
  }
  toogleIngredients() {
    this.addingIngredients = !this.addingIngredients;
  }

  constructor(private eRef:ElementRef, private postService: PostService, private ingredientService: IngredientService, private userService: RegisteredUserService) {}

  ngOnInit(): void {
    // Prevent the user from scrolling the page when the popup is open
    document.body.style.overflow = 'hidden';
    this.ingredientsList = this.ingredientService.getAllIngredients();
    this.ingredientsRemaining = this.ingredientsList;
  }

  /*Handle show list of ingredients*/
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


  addIngredient(ingredientName: string) {
    console.log(ingredientName);
    this.post.recipe.ingredients.set(ingredientName, 0);
    this.ingredientsRemaining = this.ingredientsRemaining.filter(ingredient => ingredient.name !== ingredientName);
    this.ingredientName = "";
    this.showList = false;
    this.ingredientDetailed = null;
    this.canCloseList = false;
    setTimeout(() => {
      this.canCloseList = true;
    }, 300);
  }

  removeIngredient(ingredientName: string) {
    this.canCloseList = false;

    this.post.recipe.ingredients.delete(ingredientName);
    this.ingredientsRemaining.push(this.ingredientsList.find(ingredient => ingredient.name === ingredientName)!);
    this.ingredientDetailed = null;
    this.updateTotalCalories("", 0);

    setTimeout(() => {
      this.canCloseList = true;
    }, 300);
  }

  updateTotalCalories(ingredientKey: string, newQuantity: number) {
    if(ingredientKey !== "") 
      this.post.recipe.ingredients.set(ingredientKey, newQuantity);
    this.post.recipe.totalCalories = 0;
    this.post.recipe.ingredients.forEach((value, key) => {
      // Take calories from the ingredient list
      let calories = this.ingredientsList.find(ingredient => ingredient.name === key)!.calories;
      this.post.recipe.totalCalories += (value*calories/100);
    });
    this.post.recipe.totalCalories = this.post.recipe.totalCalories.toFixed(2) as unknown as number;
  }

  submitPost() {
    let error = false;
    this.post.recipe.ingredients.forEach((value, key) => {
      if(value == 0){
        error = true;
      }
    });
    if(error){
      alert("Insert quantity for each ingredient");
      return;
    }
    if(this.post.recipe.steps.length == 0){
      alert("Insert at least one step");
      return;
    }
    if(this.post.recipe.ingredients.size == 0){
      alert("Insert at least one ingredient");
      return;
    }
    if(this.post.recipe.name == ""){
      alert("Insert a name for the recipe");
      return;
    } 
    if(this.post.description == ""){
      alert("Insert a description for the post");
      return;
    }

    // print ingredients
    for(let [key, value] of this.post.recipe.ingredients) {
      console.log(key + " " + value);
    }
    
    this.postService.uploadPost(this.post, new PostDTO(), this.userService.info).subscribe((data: boolean) => {
        if(data) {
          this.close();
        } else {
          alert("Error uploading post");
          this.close();
        }
    });
  }  

  onFileSelected(event:any) {
    if (event.target.files && event.target.files[0]) {
      const file = event.target.files[0];
  
      // Verifica il tipo MIME del file
      if (file.type.match(/image\/*/)) {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          // Qui hai la stringa base64, puoi inviarla al server o fare altre operazioni
          this.post.recipe.image = reader.result as string;
        };
        reader.onerror = (error) => {
          console.log('Error: ', error);
        };
      } else {
        alert('File non immagine selezionato');
        event.target.value = null; // Resettiamo il valore del campo di input
        // Gestisci l'errore appropriatamente
      }
    }
  }
  
  fileSetted(): boolean {
    return this.post.recipe.image != "DEFAULT";
  }

  addStep() {
    // Aggiungi il passo alla lista se non è vuoto
    if (this.stepBeingInserted !== "") {
      this.post.recipe.steps.push(this.stepBeingInserted);
      this.stepBeingInserted = "";
    }
  }

  removeStep(step: string) {
    // Trova l'indice del passo da rimuovere
    this.canCloseList = false;
    this.post.recipe.steps = this.post.recipe.steps.filter(s => s !== step);
    setTimeout(() => {
      this.canCloseList = true;
    }, 300);
  }
  

  close() {
    this.closePost.emit();
  }

  @HostListener('document:click', ['$event'])
  clickout(event: any) {
    // Close the popup if clicking outside the popup content
    if (!this.eRef.nativeElement.contains(event.target) && this.canCloseList) {
      this.close();
    }
  }
}
