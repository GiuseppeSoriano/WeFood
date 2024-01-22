import { Component, Input, OnInit } from '@angular/core';
import { RegisteredUser, RegisteredUserInterface } from '../models/registered-user.model';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { Router } from '@angular/router';
import { PostDTOInterface } from '../models/post-dto.model';
import { RegisteredUserDTOInterface } from '../models/registered-user-dto.model';

@Component({
  selector: 'app-user-personal-page',
  templateUrl: './user-personal-page.component.html',
  styleUrls: ['./user-personal-page.component.css']
})
export class UserPersonalPageComponent implements OnInit {

  @Input() users: RegisteredUserDTOInterface[] = [];
  showUsersPopup: boolean = false;
  canBeClosed = false;
  
  constructor(private router: Router, private userService: RegisteredUserService) { }
  isLoading: boolean = false;
  list_of_posts: PostDTOInterface[] = [];

  info_updated: RegisteredUserInterface = new RegisteredUser();
  
  ngOnInit(): void {
    this.getPosts();
  }

  getUser() {
    return this.userService.info;
  }

  openPopup() {
    this.showUsersPopup = true;
    setTimeout(() => {
      this.canBeClosed = true;
    }, 300);
  }

  closePopup() {
    if (this.canBeClosed) {
      // Allow the user to scroll the page again
      document.body.style.overflow = 'auto';

      this.canBeClosed = false;
      this.showUsersPopup = false;
    }
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

  modifyPersonalInfo() {
    // this.userService.modifyPersonalInformation(this.info_updated).subscribe(
    //   data => {
    //     alert(data);
    //   },
    //   error => {
    //     if (error.status === 401) {
    //       // Gestisci l'errore 401 qui
    //       alert('Wrong username or password');
    //     }
    //   }
    // );
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
//        if (error.status === 401) {
          // Gestisci l'errore 401 qui
          alert('Error in loading page');
//        }
      }
    );
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/home']);
  }

}

