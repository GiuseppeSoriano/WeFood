import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegisteredUser, RegisteredUserInterface } from '../models/registered-user.model';
import { MostPopularCombinationOfIngredientsComponent } from '../buttons/most-popular-combination-of-ingredients/most-popular-combination-of-ingredients.component';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { PostDTO, PostDTOInterface } from '../models/post-dto.model';
import { PostService } from '../services/post_service/post.service';

@Component({
  selector: 'app-registered-user-feed',
  templateUrl: './registered-user-feed.component.html',
  styleUrls: ['./registered-user-feed.component.css']
})
export class RegisteredUserFeedComponent implements OnInit {
  // info: RegisteredUserInterface = new RegisteredUser();
  isLoading = true; 
  recipeName: string = "";
  ingredientName: string = "";
  list_of_posts: PostDTOInterface[] = [];
  hours_var: number = 87600;
  limit_var: number = 30;

  post_visible: boolean = false;
  postDTO_to_be_viewed: PostDTOInterface = new PostDTO();

  creatingPost: boolean = false;
  
  logout() {
    this.userService.logout();
    this.router.navigate(['/home']);
  }
    
  goToPersonalPage() {
    this.router.navigate(['/user-personal-page']);
  }
  
  constructor(private router:Router, private postService: PostService, private userService: RegisteredUserService) {
    // const navigation = this.router.getCurrentNavigation();
    // if(navigation){
    //   const state = navigation.extras.state as {
    //     User: RegisteredUserInterface
    //   };
    //   this.info = state.User;
    // }
  }

  ngOnInit(): void {
    if(this.userService.info.username == ""){
      this.router.navigate(['/home']);
    }
    this.getPosts();
  }

  getPosts(): void {
    this.isLoading = true;
    this.postService.browseMostRecentTopRatedPosts(this.hours_var, this.limit_var).subscribe(
      data => {
        // go to registered user feed
        this.list_of_posts = data;
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

  viewPost(post: PostDTOInterface) {
    setTimeout(() => {
      document.body.style.overflow = 'hidden';
    this.postDTO_to_be_viewed = post;
    this.post_visible = true;
    }, 100);
  }

  uploadPost() {
    setTimeout(() => {
      document.body.style.overflow = 'hidden';
      this.creatingPost = true;
    }, 100);
  }
    
  closePost() {
    document.body.style.overflow = 'auto';
    this.post_visible = false;
    this.postDTO_to_be_viewed = new PostDTO();
    this.creatingPost = false;
  }
}
