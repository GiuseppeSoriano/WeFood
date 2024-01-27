import { Component, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { AdminService } from '../services/admin_service/admin.service';

@Component({
  selector: 'app-user-page-loading',
  templateUrl: './user-page-loading.component.html',
  styleUrls: ['./user-page-loading.component.css']
})
export class UserPageLoadingComponent implements OnInit {
  username: string = '';

  constructor(private router: Router, private userService: RegisteredUserService, private adminService: AdminService) {
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
    if(this.userService.info.username !== ""){
      this.userService.findRegisteredUserPageByUsername(this.username).subscribe(
        data => {
          data.posts.reverse();
          const navigationExtras: NavigationExtras = {
            state: {
              userPage: data
            }
          };
          this.router.navigate(['/user-page'], navigationExtras);
        },
        error => {
          if(error.status === 404){
            alert('User not found or banned');
            this.router.navigate(['/registered-user-feed']);
          }
        }
      );
    }
    else if (this.adminService.info.username !== ""){
      this.adminService.adminFindRegisteredUserPageByUsername(this.username).subscribe(
        data => {
          const navigationExtras: NavigationExtras = {
            state: {
              userPage: data
            }
          };
          this.router.navigate(['/user-page'], navigationExtras);
        },
        error => {
          if(error.status === 404){
            alert('User not found');
            this.router.navigate(['/admin-dashboard']);
          }
        }
      );
    }
  }

}
