import { Component, OnInit } from '@angular/core';
import { AdminService } from '../services/admin_service/admin.service';
import { PostService } from '../services/post_service/post.service';
import { IngredientService } from '../services/ingredient_service/ingredient.service';
import { PostDTOInterface } from '../models/post-dto.model';
import { Router } from '@angular/router';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  isLoading = false; 
  list_of_posts: PostDTOInterface[] = [];
  interactions: Map<string, number> = new Map<string, number>;
  filteredPosts: PostDTOInterface[] = []; // Aggiunta dell'array filtrato
  hours_var: number = 87600;
  limit_var: number = 30;
  recipeNameSearch: string = '';
  usernameSearch: string = '';
  top_ingredients: string[] = [];
  least_ingredients: string[] = [];
  newIngredient: { name: string, calories: number } = { name: '', calories: 0 };

  // info: AdminInterface = new Admin();
  logout() {
    this.adminService.logout();
    this.router.navigate(['/home']);
  }

  constructor(private router: Router, private adminService: AdminService, private postService: PostService, private ingredientService: IngredientService, private userService: RegisteredUserService) {
    const navigation = this.router.getCurrentNavigation();
    // if(navigation){
    //   const state = navigation.extras.state as {
    //     Admin: AdminInterface
    //   };
    //   this.info = state.Admin;
    // }
  }

  ngOnInit(): void {
    if (this.adminService.info.username == "") {
      this.router.navigate(['/home']);
    }
    //this.getPosts();
    this.getStatistics();
    this.getTopIngredients();
    this.getLeastIngredients();
  }
  getLeastIngredients() {
    this.ingredientService.findMostLeastUsedIngredients(true).subscribe(
      data => {
        // go to registered user feed
        this.top_ingredients = data;
      },
      error => {
        //        if (error.status === 401) {
        // Gestisci l'errore 401 qui
        alert('Error in loading page');
        //        }
      }
    );
  }
  getTopIngredients() {
    this.ingredientService.findMostLeastUsedIngredients(false).subscribe(
      data => {
        // go to registered user feed
        this.least_ingredients = data;
      },
      error => {
        //        if (error.status === 401) {
        // Gestisci l'errore 401 qui
        alert('Error in loading page');
        //        }
      }
    );
  }

  getPosts(): void {
    this.isLoading = true;
    this.postService.browseMostRecentTopRatedPosts(this.hours_var, this.limit_var).subscribe(
      data => {
        // go to registered user feed
        this.list_of_posts = data;
        this.filteredPosts = this.list_of_posts;
        //this.filterPosts(); // Chiamare il metodo di filtro dopo aver ottenuto i post
        this.isLoading = false;
      },
      error => {
        //        if (error.status === 401) {
        // Gestisci l'errore 401 qui
        alert('Error in loading page');
        //        }
      }
    );
  }

  getStatistics(): void {
    this.postService.interactionsAnalysis().subscribe(
      data => {
        // go to registered user feed
        this.interactions = data;
        this.interactionValues = {
          "avgOfStarRanking": {
            "IMAGE": this.interactions.get("IMAGEAvgOfStarRanking"),
            "NOIMAGE": this.interactions.get("NOIMAGEAvgOfStarRanking")
          },
          "ratioOfComments": {
            "IMAGE": this.interactions.get("IMAGEratioOfComments"),
            "NOIMAGE": this.interactions.get("NOIMAGEratioOfComments")
          },
          "ratioOfStarRankings": {
            "IMAGE": this.interactions.get("IMAGEratioOfStarRankings"),
            "NOIMAGE": this.interactions.get("NOIMAGEratioOfStarRankings"),
          }
        };
      },
      error => {
        //        if (error.status === 401) {
        // Gestisci l'errore 401 qui
        alert('Error in loading page');
        //        }
      }
    );
  }

  // Metodo per filtrare i post
  /*
  filterPosts() {
    this.filteredPosts = this.list_of_posts.filter(item =>
      item.recipeName.toLowerCase().includes(this.recipeNameSearch.toLowerCase()) &&
      item.username.toLowerCase().includes(this.usernameSearch.toLowerCase())
    );
  }
  */

  // Chiamato quando il valore della barra di ricerca per RecipeName cambia
  searchRecipe() {
    alert(this.recipeNameSearch);
    this.isLoading = true;
    this.postService.findPostsByRecipeName(this.recipeNameSearch).subscribe(
      data => {
        // go to registered user feed
        this.list_of_posts = data;
        this.filteredPosts = this.list_of_posts;
        //this.filterPosts(); // Chiamare il metodo di filtro dopo aver ottenuto i post
        this.isLoading = false;
      },
      error => {
        //        if (error.status === 401) {
        // Gestisci l'errore 401 qui
        alert('Error in loading page');
        //        }
      }
    );
  }

  // Chiamato quando il valore della barra di ricerca per Username cambia
  searchUser() {
    alert(this.usernameSearch);
    
  }

  // Metodo per aggiungere un ingrediente
  createIngredient(): void {
    if (this.newIngredient.name && this.newIngredient.calories > 0) {
      // Puoi implementare la chiamata al servizio per aggiungere l'ingrediente qui
      alert(`Ingrediente aggiunto:\nNome: ${this.newIngredient.name}\nCalorie: ${this.newIngredient.calories}`);
      // Azzera il form dopo l'aggiunta
      this.newIngredient = { name: '', calories: 0 };
    } else {
      alert('Invalid input.');
    }
  }

  interactionKeys = ["avgOfStarRanking", "ratioOfComments", "ratioOfStarRankings"];
  interactionValues: any = {
    "avgOfStarRanking": {
      "IMAGE": this.interactions.get("IMAGEAvgOfStarRanking"),
      "NOIMAGE": this.interactions.get("NOIMAGEAvgOfStarRanking")
    },
    "ratioOfComments": {
      "IMAGE": this.interactions.get("IMAGEratioOfComments"),
      "NOIMAGE": this.interactions.get("NOIMAGEratioOfComments")
    },
    "ratioOfStarRankings": {
      "IMAGE": this.interactions.get("IMAGEratioOfStarRankings"),
      "NOIMAGE": this.interactions.get("NOIMAGEratioOfStarRankings"),
    }
  };

}
