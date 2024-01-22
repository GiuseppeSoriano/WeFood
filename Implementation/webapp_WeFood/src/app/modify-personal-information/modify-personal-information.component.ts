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
  confirmPassword: string = '';
  popupPassword: string = '';
  info: RegisteredUserInterface = new RegisteredUser();
  passwordsMatch: boolean = true;

  constructor(private userService: RegisteredUserService, private eRef: ElementRef) {}

  ngOnInit(): void {
    // Chiamata a getUser() o altro codice iniziale se necessario
  }

  close() {
    this.password = '';
    this.confirmPassword = '';
    this.popupPassword = '';
    this.passwordsMatch = true;
    this.closePopup.emit();
  }

  updateUserInfo() {
    if (this.password !== this.confirmPassword) {
      this.passwordsMatch = false;
      return;
    }

    // Raccolti i dati da inviare al server (username, name, surname, password)
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
