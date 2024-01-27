import { Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { RegisteredUserDTO } from '../models/registered-user-dto.model';
import { NavigationExtras, Router } from '@angular/router';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';
import { RegisteredUserPage } from '../models/registered-user-page.model';

@Component({
  selector: 'app-view-list-of-users',
  templateUrl: './view-list-of-users.component.html',
  styleUrls: ['./view-list-of-users.component.css']
})
export class ViewListOfUsersComponent implements OnInit {
  @Input() labelUsers: string = '';
  @Input() users: RegisteredUserDTO[] = [];
  @Output() closePopup: EventEmitter<void> = new EventEmitter();
  constructor(private router: Router, private userService: RegisteredUserService, private eRef: ElementRef) { }

  ngOnInit(): void {
  }

  showClickedUserPage() {
    //da definire
  }

  close() {
    this.closePopup.emit();
  }

  @HostListener('document:click', ['$event'])
  clickout(event: any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.close();
    }
  }

  @Output() goUserFromListOfUsers: EventEmitter<void> = new EventEmitter();

  goToUserPage(user: RegisteredUserDTO) {
    this.close();
    if(this.userService.info.username == user.username) {
      this.router.navigate(['/user-personal-page']);
      return;
    }
    const navigationExtras: NavigationExtras = {
      state: {
        username: user.username
      }
    };
    this.router.navigate(['/user-page-loading'], navigationExtras);
  }
}

