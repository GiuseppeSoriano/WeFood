import { Component, Input, OnInit } from '@angular/core';
import { RegisteredUser, RegisteredUserInterface } from '../models/registered-user.model';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { NavigationExtras, Router } from '@angular/router';
import { PostDTO, PostDTOInterface } from '../models/post-dto.model';
import { RegisteredUserDTO, RegisteredUserDTOInterface } from '../models/registered-user-dto.model';
import { IngredientService } from '../services/ingredient_service/ingredient.service';
import { PostService } from '../services/post_service/post.service';

@Component({
  selector: 'app-user-personal-page',
  templateUrl: './user-personal-page.component.html',
  styleUrls: ['./user-personal-page.component.css']
})
export class UserPersonalPageComponent implements OnInit {

  @Input() users: RegisteredUserDTOInterface[] = [];
  showUsersPopup: boolean = false;
  canBeClosed = false;
  avgTotalCalories: number = 0;
  usersToFollowBasedOnFriends: RegisteredUserDTOInterface[] = [];
  mostFollowed: RegisteredUserDTOInterface[] = [];
  labelUsers: string = '';
  
  constructor(private router: Router, private userService: RegisteredUserService, private ingredientService: IngredientService, private postService: PostService) { }
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
    getAvgTotalCalories() {
      this.postService.averageTotalCaloriesByUser(this.getUser().username).subscribe(
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
          this.usersToFollowBasedOnFriends = data1.filter(user => user.username != this.getUser().username);
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
    this.ingredientService.findMostUsedIngredientsByUser(new RegisteredUserDTO(this.getUser().id, this.getUser().username)).subscribe(
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

  showFollowers() {
    this.userService.findFollowers().subscribe(
      data => {
        this.users = data;
        this.labelUsers = 'Followers';
        this.openPopup();
      },
      error => {
        console.log(error);
      }
    );
  }

  showFollowed() {
    this.users = this.userService.usersFollowed;
    this.labelUsers = 'Followed';
    this.openPopup();
  }

  showFriends() {
    this.userService.findFriends().subscribe(
      data => {
        this.users = data;
        this.labelUsers = 'Friends';
        this.openPopup();
      },
      error => {
        console.log(error);
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
        this.list_of_posts = data.posts.reverse();
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

  goToUserPage(user: RegisteredUserDTOInterface) {
    if(this.getUser().username == user.username) {
      this.router.navigate(['/user-personal-page']);
      return;
    }
    const navigationExtras: NavigationExtras = {
      state: {
        username: user.username
      }
    };
    this.router.navigate(['/user-page-loading'], navigationExtras);
  }
  
}

