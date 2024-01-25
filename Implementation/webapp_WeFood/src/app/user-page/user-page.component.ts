import { Component, Input, OnInit } from '@angular/core';
import { PostDTO, PostDTOInterface } from '../models/post-dto.model';
import { RegisteredUser, RegisteredUserInterface } from '../models/registered-user.model';
import { Router } from '@angular/router';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { IngredientService } from '../services/ingredient_service/ingredient.service';
import { PostService } from '../services/post_service/post.service';
import { RegisteredUserDTO, RegisteredUserDTOInterface } from '../models/registered-user-dto.model';
import { RegisteredUserPage, RegisteredUserPageInterface } from '../models/registered-user-page.model';
import { AdminService } from '../services/admin_service/admin.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  
  @Input() users: RegisteredUserDTOInterface[] = [];
  showUsersPopup: boolean = false;
  canBeClosed = false;
  avgTotalCalories: number = 0;
  usersToFollowBasedOnFriends: RegisteredUserDTOInterface[] = [];
  mostFollowed: RegisteredUserDTOInterface[] = [];

  userPage: RegisteredUserPageInterface;

  constructor(private router: Router, private userService: RegisteredUserService, private ingredientService: IngredientService, private postService: PostService, private adminService: AdminService) {
    console.log("IM IN USER PAGE");
    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as {
      userPage: RegisteredUserPageInterface
    };
    this.userPage = state.userPage;
    // this.userPage = new RegisteredUserPage("PIPPO", "PAPERINO", []); 
  }


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
    this.getAvgTotalCalories();
    this.getSuggestions();
  }

  isUser(){
    return this.userService.info.username !== "";
  }

  isAdmin(){
    return this.adminService.info.username !== "";
  }

    getAvgTotalCalories() {
      this.postService.averageTotalCaloriesByUser(this.userPage.username).subscribe(
        data => {
          this.avgTotalCalories = data;
        },
        error => {
          alert('Error in loading calories');
        }
      );
    }
    getSuggestions() {
      this.userService.findUsersToFollowBasedOnUserFriends().subscribe(
        data1 => {
          this.usersToFollowBasedOnFriends = data1;
        },
        error => {
          alert('Error in loading suggestions');
        }
      );
      this.userService.findMostFollowedUsers().subscribe(
        data2 => {
          this.mostFollowed = data2;
        },
        error => {
          alert('Error in loading suggestions');
        }
      );
    }

  getUser() {
    return this.userService.info;
  }

  getTopIngredients() {
    this.ingredientService.findMostUsedIngredientsByUser(new RegisteredUserDTO(this.userPage.id, this.userPage.username)).subscribe(
      data => {
        this.top_ingredients = data;
      },
      error => {
        alert('Error in loading ingredients');
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

  goToDashboard() {
    this.router.navigate(['/admin-dashboard']);
  }

  showFollowers() {
    this.userService.findFollowers(new RegisteredUserDTO(this.userPage.id, this.userPage.username)).subscribe(
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
    this.userService.findFollowed(new RegisteredUserDTO(this.userPage.id, this.userPage.username)).subscribe(
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
    this.userService.findFriends(new RegisteredUserDTO(this.userPage.id, this.userPage.username)).subscribe(
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
    this.list_of_posts = this.userPage.posts;
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
