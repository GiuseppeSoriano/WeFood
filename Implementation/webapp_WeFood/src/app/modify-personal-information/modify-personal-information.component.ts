// Nel tuo componente TypeScript
import { Component, ElementRef, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { RegisteredUserDTO } from '../models/registered-user-dto.model';
import { RegisteredUser, RegisteredUserInterface } from '../models/registered-user.model';

@Component({
  selector: 'app-modify-personal-information',
  templateUrl: './modify-personal-information.component.html',
  styleUrls: ['./modify-personal-information.component.css']
})
export class ModifyPersonalInformationComponent implements OnInit {

  @Input() users: RegisteredUserDTO[] = [];
  @Output() closePopup: EventEmitter<void> = new EventEmitter();

  password: string = '';
  newPassword: string = '';
  confirmNewPassword: string = '';
  popupPassword: string = '';
  info: RegisteredUserInterface = new RegisteredUser();
  passwordsMatch: boolean = true;
  changePassword: boolean = false;
  response: boolean = false;
  response_msg: string = "Test Message";

  constructor(private userService: RegisteredUserService, private eRef: ElementRef) {}

  ngOnInit(): void {
    // Chiamata a getUser() o altro codice iniziale se necessario
  }

  close() {
    this.password = '';
    this.newPassword = '';
    this.confirmNewPassword = '';
    this.popupPassword = '';
    this.passwordsMatch = true;
    this.closePopup.emit();
  }

  toggleNewPassword() {
    this.newPassword = '';
    this.confirmNewPassword = '';
    this.changePassword = !this.changePassword;
  }

  updateUserInfo() {
    if (this.password !== this.confirmNewPassword) {
      this.passwordsMatch = false;
      return;
    }

    // TESTTT
    this.response = true;
    this.response_msg = "Test Message";
    return

    const updatedData = {
      username: this.info.username,
      name: this.info.name,
      surname: this.info.surname,
      password: this.password
    };

    alert(updatedData.username + ", " + updatedData.password + ", " + this.popupPassword);

    /*
    // Chiamata alla tua API per aggiornare le informazioni utente
    this.userService.updateUserInfo(updatedData)
      .subscribe(response => {
        alert('Update successful!');
        this.close();
      }, error => {
        alert('Update failed. Please check your password and try again.');
      });
    */  
    }
}
