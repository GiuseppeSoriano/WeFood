import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { NavigationExtras, Router } from '@angular/router';

@Component({
  selector: 'app-logging-popup',
  templateUrl: './logging-popup.component.html',
  styleUrls: ['./logging-popup.component.css']
})
export class LoggingPopupComponent implements OnInit {

  username: string = "";
  password: string = "";
  adminLogin: boolean = false;

  @Output() closePopup: EventEmitter<void> = new EventEmitter();
  
  constructor(private userService: RegisteredUserService, private router:Router) { }

  ngOnInit(): void {
  }

  close() {
    this.closePopup.emit();
  }

  onLogin() {
    this.userService.login(this.username, this.password).subscribe(
      data => {
        this.closePopup.emit();
        // go to registered user feed
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
          alert('Wrong username or password');
        }
      }
    );
    this.username = "";
    this.password = "";
  }

  loginAsUser() {
    this.onLogin();
  }

  loginAsAdmin() {

  }

  switchLogin() {
    this.adminLogin = !this.adminLogin;
    this.username = "";
    this.password = "";
  }
}
