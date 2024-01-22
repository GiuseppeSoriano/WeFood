// upload-post.component.ts
import { Component, OnInit } from '@angular/core';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { Post, PostInterface } from 'src/app/models/post.model';
import { Recipe, RecipeInterface } from 'src/app/models/recipe.model';
import { IngredientService } from 'src/app/services/ingredient_service/ingredient.service';
import { PostService } from 'src/app/services/post_service/post.service';
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

  post: PostInterface = new Post();
  recipe: RecipeInterface = new Recipe();

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
  newIngredientIsBeingInserted() {
    this.showList=true;
  }

  //uploader: FileUploader = new FileUploader({});

  constructor(private postService: PostService, private ingredientService: IngredientService) {}

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
    this.recipe.ingredients.set(ingredientName, 0);
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

    this.recipe.ingredients.delete(ingredientName);
    this.ingredientsRemaining.push(this.ingredientsList.find(ingredient => ingredient.name === ingredientName)!);
    this.ingredientDetailed = null;

    setTimeout(() => {
      this.removingIngredient = false;
    }, 300);
  }

  submitPost() {
    //Sistemare
    alert(this.recipe.ingredients.size);

  }


  submitIngredient() {
    console.log(!this.checkIngredientExists(this.ingredientName.toLowerCase()));
    if (!this.checkIngredientExists(this.ingredientName.toLowerCase()) && this.quantity > 0 && this.ingredientName != "") {
      // L'ingrediente non esiste ancora, aggiungilo alla lista
  
      // Aggiungi l'ingrediente alla ricetta
      this.recipe.ingredients.set(this.ingredientName.toLowerCase(), this.quantity);
  
      // Pulisci i campi dopo l'aggiunta
      this.ingredientName = "";
      this.quantity = 0;
    } else {
      // L'ingrediente esiste già, gestisci come preferisci (ad esempio, mostra un messaggio di avviso)
      alert("L'ingrediente esiste già nella lista o non è stato inseriro o la quantità è < 0.");
    }
  }

  checkIngredientExists(ingredientName: string): boolean {
    return this.recipe.ingredients.has(ingredientName);
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
      this.recipe.steps.push(this.stepBeingInserted);
      this.stepBeingInserted = "";
    }
  }

  removeStep(step: string) {
    // Trova l'indice del passo da rimuovere
    this.recipe.steps = this.recipe.steps.filter(s => s !== step);
  }
  

  close() {
  }
}

/*
Fare npm install ng2-file-upload
così da poter utilizare l'image uploader
togliere i commenti nei vari file per far funzionare l'uploader
ritornare l'oggetto e gestire la grafica
vedere altri piccoli dettagli
*/

