import { Component, Input, OnInit } from '@angular/core';
import { RegisteredUser, RegisteredUserInterface } from '../models/registered-user.model';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { Router } from '@angular/router';
import { PostDTO, PostDTOInterface } from '../models/post-dto.model';
import { RegisteredUserDTOInterface } from '../models/registered-user-dto.model';
import { IngredientService } from '../services/ingredient_service/ingredient.service';

@Component({
  selector: 'app-user-personal-page',
  templateUrl: './user-personal-page.component.html',
  styleUrls: ['./user-personal-page.component.css']
})
export class UserPersonalPageComponent implements OnInit {

  @Input() users: RegisteredUserDTOInterface[] = [];
  showUsersPopup: boolean = false;
  canBeClosed = false;
  
  constructor(private router: Router, private userService: RegisteredUserService, private ingredientService: IngredientService) { }
  isLoading: boolean = false;
  list_of_posts: PostDTOInterface[] = [];

  info_updated: RegisteredUserInterface = new RegisteredUser();

  modifyPersonalInfoPopup: boolean = false;

  top_ingredients: string[] = [];
  
  post_visible: boolean = false;
  @Input() postDTO_to_be_viewed: PostDTOInterface = new PostDTO();
  
  ngOnInit(): void {
    this.getPosts();
    this.getTopIngredients();
    console.log(this.top_ingredients);
  }

  getUser() {
    return this.userService.info;
  }

  getTopIngredients() {
    this.ingredientService.findMostUsedIngredientsByUser(this.getUser()).subscribe(
      data => {
        this.top_ingredients = data;
      },
      error => {
        alert('Error in loading page');
      }
    );
  }

  private openPopup() {
    this.canBeClosed = false;
    setTimeout(() => {
      this.showUsersPopup = true;
      this.canBeClosed = true;
    }, 100);
  }

  closePopup() {
    if (this.canBeClosed) {
      // Allow the user to scroll the page again
      document.body.style.overflow = 'auto';

      this.canBeClosed = false;
      this.showUsersPopup = false;
      this.modifyPersonalInfoPopup = false;
      this.post_visible = false;
    }
  }

  closePost() {
    this.closePopup();
    this.getPosts();
  }

  goToHomePage() {
    this.router.navigate(['/registered-user-feed']);
  }

  showFollowers() {
    this.userService.findFollowers().subscribe(
      data => {
        this.users = data;
        this.openPopup();
      },
      error => {
        if (error.status === 401) {
          // Gestisci l'errore 401 qui
          alert('Wrong username or password');
        }
      }
    );
  }

  showFollowed() {
    this.userService.findFollowed().subscribe(
      data => {
        this.users = data;
        this.openPopup();
      },
      error => {
        if (error.status === 401) {
          // Gestisci l'errore 401 qui
          alert('Wrong username or password');
        }
      }
    );
  }

  showFriends() {
    this.userService.findFriends().subscribe(
      data => {
        this.users = data;
        this.openPopup();
      },
      error => {
        if (error.status === 401) {
          // Gestisci l'errore 401 qui
          alert('Wrong username or password');
        }
      }
    );
  }

  showSelectedPost() {
    // Implementa la logica per visualizzare i post selezionati
  }

  getPosts(): void {
    this.isLoading = true;
    this.userService.findRegisteredUserPageByUsername(this.userService.info.username).subscribe(
      data => {
        // go to registered user feed
        this.list_of_posts = data.posts;
        this.isLoading = false;
      },
      error => {
        alert('Error in loading page');
      }
    );
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/home']);
  }

  viewPost(post: PostDTOInterface) {
    this.canBeClosed = false;
    setTimeout(() => {
      document.body.style.overflow = 'hidden';
      this.postDTO_to_be_viewed = post;
      this.post_visible = true;
      this.canBeClosed = true;
    }, 100);
  }

  modifyPersonalInfo() {
    this.canBeClosed = false;
    setTimeout(() => {
      document.body.style.overflow = 'hidden';
      this.modifyPersonalInfoPopup = true;
      this.canBeClosed = true;
    }, 100);
  }

}

