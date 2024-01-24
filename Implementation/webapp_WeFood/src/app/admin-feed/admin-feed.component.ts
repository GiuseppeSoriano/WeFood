import { Component, OnInit } from '@angular/core';
import { PostDTO, PostDTOInterface } from '../models/post-dto.model';
import { Router } from '@angular/router';
import { PostService } from '../services/post_service/post.service';
import { AdminService } from '../services/admin_service/admin.service';

@Component({
  selector: 'app-admin-feed',
  templateUrl: './admin-feed.component.html',
  styleUrls: ['./admin-feed.component.css']
})
export class AdminFeedComponent implements OnInit {


  // info: RegisteredUserInterface = new RegisteredUser();
  isLoading = true; 
  recipeName: string = "";
  ingredientName: string = "";
  list_of_posts: PostDTOInterface[] = [];
  hours_var: number = 87600;
  limit_var: number = 30;

  post_visible: boolean = false;
  postDTO_to_be_viewed: PostDTOInterface = new PostDTO();
  
  logout() {
    this.adminService.logout();
    this.router.navigate(['/home']);
  }

  goToDashboard() {
    this.router.navigate(['/admin-dashboard']);
  }
  
  constructor(private router:Router, private postService: PostService, private adminService: AdminService) {
    // const navigation = this.router.getCurrentNavigation();
    // if(navigation){
    //   const state = navigation.extras.state as {
    //     User: RegisteredUserInterface
    //   };
    //   this.info = state.User;
    // }
  }

  ngOnInit(): void {
    if(this.adminService.info.username == ""){
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
    
  closePost() {
    document.body.style.overflow = 'auto';
    this.post_visible = false;
    this.postDTO_to_be_viewed = new PostDTO();
  }

}
