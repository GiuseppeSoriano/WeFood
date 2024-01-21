import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { PostService } from '../services/post_service/post.service';
import { PostDTOInterface } from '../models/post-dto.model';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { AdminService } from '../services/admin_service/admin.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})


export class HomeComponent implements OnInit {
  isLoading = true; 
  showLoginPopup: boolean = false;
  list_of_posts: PostDTOInterface[] = [];

  hours_var: number = 87600;
  limit_var: number = 30;

  canBeClosed = false;

  constructor(private postService: PostService, private userService: RegisteredUserService, private adminService: AdminService) { }

  ngOnInit(): void {
    this.getPosts();
    this.userService.logout();
    this.adminService.logout();
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

  openPopup() {
    this.showLoginPopup = true;
    setTimeout(() => {
      this.canBeClosed = true;
    }, 300);
  }

  closePopup() {
    if (this.canBeClosed) {
      // Allow the user to scroll the page again
      document.body.style.overflow = 'auto';

      this.canBeClosed = false;
      this.showLoginPopup = false;
    }
  }
}
