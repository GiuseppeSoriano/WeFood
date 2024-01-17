import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';

@Component({
  selector: 'app-logging-popup',
  templateUrl: './logging-popup.component.html',
  styleUrls: ['./logging-popup.component.css']
})
export class LoggingPopupComponent implements OnInit {

  username: string = "";
  password: string = "";
  user: any;
  adminLogin: boolean = false;

  @Output() closePopup: EventEmitter<void> = new EventEmitter();
  
  constructor(private userService: RegisteredUserService) { }

  ngOnInit(): void {
  }

  close() {
    this.closePopup.emit();
  }

  onLogin() {
    this.userService.login(this.username, this.password).subscribe(
      data => {
        this.user = data;
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
