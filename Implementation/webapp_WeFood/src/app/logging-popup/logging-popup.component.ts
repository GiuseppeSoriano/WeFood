import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { NavigationExtras, Router } from '@angular/router';
import { AdminService } from '../services/admin_service/admin.service';
import { UnregisteredUserService } from '../services/unregistered_user_service/unregistered-user.service';

@Component({
  selector: 'app-logging-popup',
  templateUrl: './logging-popup.component.html',
  styleUrls: ['./logging-popup.component.css']
})
export class LoggingPopupComponent implements OnInit {

  username: string = "";
  password: string = "";
  confirmPassword: string = "";
  name: string = "";
  surname: string = "";
  adminLogin: boolean = false;
  signUp: boolean = false;
  response: boolean = false;
  response_msg: string = "Response message";

  @Output() closePopup: EventEmitter<void> = new EventEmitter();
  
  constructor(private unregisteredUserService: UnregisteredUserService, private userService: RegisteredUserService, private adminService: AdminService, private router:Router, private eRef:ElementRef) { }

  ngOnInit(): void {
    // Prevent the user from scrolling the page when the popup is open
    document.body.style.overflow = 'hidden';
  }

  close() {
    this.closePopup.emit();
  }

  onLogin() {
    this.userService.login(this.username, this.password);
    if(this.userService.info == null){
      this.response = true;
      this.response_msg = "Wrong username or password";
      return;
    }

    this.closePopup.emit();
    this.router.navigate(['/registered-user-feed']);

    this.username = "";
    this.password = "";
  }

  loginAsUser() {
    this.onLogin();
  }

  loginAsAdmin() {
    this.adminService.login(this.username, this.password).subscribe(
      data => {
        this.closePopup.emit();
        // go to Admin Dashboard
        const navigationExtras: NavigationExtras = {
          state: {
            Admin: data,
          }
        };
        this.router.navigate(['/admin-dashboard'], navigationExtras);
      },
      error => {
        if (error.status === 401) {
          // Gestisci l'errore 401 qui
          this.response = true;
          this.response_msg = "Wrong username or password";
        }
      }
    );
    this.username = "";
    this.password = "";
  }

  register() {
    if(this.username == "" || this.password == "" || this.name == "" || this.surname == "" || this.confirmPassword == ""){
      this.response = true;
      this.response_msg = "Please, fill all the fields.";
      return;
    }
    this.response = false;
    if(this.password != this.confirmPassword){
      return;
    }
    this.unregisteredUserService.register(this.username, this.password, this.name, this.surname).subscribe(
      data => {
        this.closePopup.emit();
        // go to RegisteredUser feed
        const navigationExtras: NavigationExtras = {
          state: {
            User: data,
          }
        };
        this.router.navigate(['/registered-user-feed'], navigationExtras);
      },
      error => {
        if (error.status === 401) {
          // Gestisci l'errore 401 qui
          this.response = true;
          this.response_msg = "Username not available."
        }
      }
    );
    this.username = "";
    this.name = "";
    this.surname = "";
    this.password = "";
    this.confirmPassword = "";
  }

  switchLogin() {
    this.adminLogin = !this.adminLogin;
    this.username = "";
    this.password = "";
    this.response = false;
  }

  switchSignUp() {
    setTimeout(() => {
      this.signUp = !this.signUp;
      this.username = "";
      this.name = "";
      this.surname = "";
      this.password = "";
      this.confirmPassword = "";
      this.response = false;
    }, 50);
  }


  @HostListener('document:click', ['$event'])
  clickout(event:any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.close();
    }
  }
}
