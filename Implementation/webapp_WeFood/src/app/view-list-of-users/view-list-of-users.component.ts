import { Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { RegisteredUserDTO } from '../models/registered-user-dto.model';
import { Router } from '@angular/router';
import { RegisteredUserService } from '../services/registered_user_service/registered-user.service';

@Component({
  selector: 'app-view-list-of-users',
  templateUrl: './view-list-of-users.component.html',
  styleUrls: ['./view-list-of-users.component.css']
})
export class ViewListOfUsersComponent implements OnInit {
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
}

