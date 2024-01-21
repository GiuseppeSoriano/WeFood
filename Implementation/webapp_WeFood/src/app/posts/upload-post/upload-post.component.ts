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
  ingredientsList: IngredientInterface[] = [];
  step: string = "";
  ingredientName: string = "";
  quantity: number = 0;
  //uploader: FileUploader = new FileUploader({});

  constructor(private postService: PostService, private ingredientService: IngredientService) {}

  submitPost() {
    //Sistemare
    alert(this.recipe.ingredients.size);

  }

  submitStep() {
    this.recipe.steps.push(this.step);
    this.step = "";
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

  submitImage() {
    // Verifica se ci sono elementi nella coda di caricamento
    /*
    if (this.uploader.queue.length > 0) {
      // Ottieni il primo elemento della coda (puoi adattare questa logica in base alle tue esigenze)
      const fileItem: FileItem = this.uploader.queue[0];
  
      // Aggiungi l'immagine alla tua ricetta
      this.recipe.image = fileItem._file.name;
  
      // Esegui il caricamento effettivo (puoi personalizzare questa parte in base alle tue esigenze)
      fileItem.upload();
    } else {
      alert("Nessun file nella coda di caricamento.");
    }
    */
  }

  removeStep(step: string) {
    // Trova l'indice del passo da rimuovere
    const index = this.recipe.steps.indexOf(step);
  
    // Rimuovi il passo dalla lista se l'indice è valido
    if (index !== -1) {
      this.recipe.steps.splice(index, 1);
    }
  }
  
  removeIngredient(key: string) {
    // Rimuovi l'ingrediente dalla mappa degli ingredienti
    this.recipe.ingredients.delete(key);
  }

  ngOnInit(): void {
    // Prevent the user from scrolling the page when the popup is open
    document.body.style.overflow = 'hidden';
  
    // Configura il caricatore di file
    /*this.uploader.onAfterAddingFile = (fileItem: FileItem) => {
      fileItem.withCredentials = false;
    };
    */
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

