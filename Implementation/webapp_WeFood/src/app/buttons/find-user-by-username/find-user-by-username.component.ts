import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin_service/admin.service';
import { RegisteredUserService } from 'src/app/services/registered_user_service/registered-user.service';

@Component({
  selector: 'app-find-user-by-username',
  templateUrl: './find-user-by-username.component.html',
  styleUrls: ['./find-user-by-username.component.css']
})
export class FindUserByUsernameComponent implements OnInit {

  usernameSearch: string = "";

  constructor(private router: Router, private eRef: ElementRef, 
    private userService: RegisteredUserService, private adminService: AdminService) { }

  ngOnInit(): void {
  }

  activeDropdownIndex: boolean = false;

  toggleDropdown(): void {
    this.activeDropdownIndex = !this.activeDropdownIndex;
  }

  isActiveDropdown(): boolean {
    return this.activeDropdownIndex;
  }

  searchUser() {
    if(this.userService.info.username !== ""){
      this.userService.findRegisteredUserPageByUsername(this.usernameSearch).subscribe(
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
            alert('User not found or banned');
          }
        }
      );
    }
    else{
      this.adminService.adminFindRegisteredUserPageByUsername(this.usernameSearch).subscribe(
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
          }
        }
      );
    }
  }

  @HostListener('document:click', ['$event'])
  clickout(event:any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      if (this.isActiveDropdown()) {
        this.toggleDropdown();
      }
    }
  }
}
