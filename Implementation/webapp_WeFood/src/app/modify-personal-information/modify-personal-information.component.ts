// Nel tuo componente TypeScript
import { Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { RegisteredUserInterface } from '../models/registered-user.model';
import * as bcrypt from 'bcryptjs';
import { Router } from '@angular/router';
import { PostDTOInterface } from '../models/post-dto.model';

@Component({
  selector: 'app-modify-personal-information',
  templateUrl: './modify-personal-information.component.html',
  styleUrls: ['./modify-personal-information.component.css']
})
export class ModifyPersonalInformationComponent implements OnInit {

  @Input() postDTOs: PostDTOInterface[] = [];
  @Output() closePopup: EventEmitter<void> = new EventEmitter();

  password: string = '';
  popupPassword: string = '';
  changePassword: boolean = false;
  deletingAccount: boolean = false;

  newName: string = '';
  newSurname: string = '';
  currentPassword: string = '';
  newPassword: string = '';
  confirmNewPassword: string = '';

  response: boolean = false;
  response_msg: string = "Test Message";

  canClose: boolean = true;

  constructor(private userService: RegisteredUserService, private eRef: ElementRef, private router: Router) {}

  ngOnInit(): void {
    // Chiamata a getUser() o altro codice iniziale se necessario
    this.newName = this.userService.info.name;
    this.newSurname = this.userService.info.surname;
  }

  close() {
    this.password = '';
    this.newPassword = '';
    this.confirmNewPassword = '';
    this.popupPassword = '';
    this.closePopup.emit();
  }

  deleteRegisteredUser() {
    // Fare logout e mandare query al server
    this.userService.deleteUser(this.postDTOs).subscribe(
      data => {
        if(data) {
          this.userService.logout();
          this.close();
          this.router.navigate(['/home']);
        }
        else {
          alert('Error in deleting user');
        }
      },
      error => {
        alert('Error in deleting user');
      }
    );
  }

  cancelDeleteAccount() {
    setTimeout(() => {
      this.deletingAccount = false;
    }, 50);
  }

  deleteAccount() {
    setTimeout(() => {
      this.deletingAccount = true;
    }, 50);
  }

  toggleNewPassword() {
    this.canClose = false;
    setTimeout(() => {
      this.newPassword = '';
      this.confirmNewPassword = '';
      this.changePassword = !this.changePassword;
      this.canClose = true;  
    }, 100);
  }

  updateUserInfo() {
    if (this.newPassword !== this.confirmNewPassword) {
      return;
    }  

    bcrypt.compare(this.currentPassword, this.userService.info.password, (err, res) => {
      if (res) {
        const newCredentials: RegisteredUserInterface = {
          id: this.userService.info.id,
          username: this.userService.info.username,
          name: this.newName,
          surname: this.newSurname,
          password: this.newPassword == '' ? this.currentPassword : this.newPassword
        };    
    
        this.userService.modifyPersonalInformation(newCredentials)
          .subscribe(response => {
            if(response){
              this.response = true;
              this.response_msg = "Update successful!";
              this.userService.info = newCredentials;
            }
            else{
              this.response = true;
              this.response_msg = "Update failed. Please try again.";
            }
          }, error => {
            alert('Error contacting the server. Please try again.');
          });
        
      } else {
        this.response = true;
        this.response_msg = "Wrong password";
        return;
      }
    }
    );

    }

    @HostListener('document:click', ['$event'])
    clickout(event: any) {
      // Close the popup if clicking outside the popup content
      if (!this.eRef.nativeElement.contains(event.target) && this.canClose) {
        this.close();
      }
    }
}
