import { Component, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';

@Component({
  selector: 'app-user-page-loading',
  templateUrl: './user-page-loading.component.html',
  styleUrls: ['./user-page-loading.component.css']
})
export class UserPageLoadingComponent implements OnInit {
  username: string = '';

  constructor(private router: Router, private userService: RegisteredUserService) {
    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as {
      username: string
    };
    this.username = state.username;
  }

  ngOnInit(): void {
    this.goToUserPage();
  }

  goToUserPage() {
    this.userService.findRegisteredUserPageByUsername(this.username).subscribe(
      data => {
        const navigationExtras: NavigationExtras = {
          state: {
            userPage: data
          }
        };
        this.router.navigate(['/user-page'], navigationExtras);
      },
      error => {
        alert('Error in loading page');
      }
    );
  }

}
