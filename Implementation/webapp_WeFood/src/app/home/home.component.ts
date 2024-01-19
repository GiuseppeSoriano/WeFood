import { Component, OnInit } from '@angular/core';
import { PostService } from '../services/post_service/post.service';
import { PostDTOInterface } from '../models/post-dto.model';

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

  constructor(private postService: PostService) { }

  ngOnInit(): void {
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

  openPopup() {
    this.showLoginPopup = true;
  }

  closePopup() {
    this.showLoginPopup = false;
  }

}
