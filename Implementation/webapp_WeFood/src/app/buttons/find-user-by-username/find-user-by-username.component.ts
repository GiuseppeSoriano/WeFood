import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { RegisteredUserService } from 'src/app/services/registered_user_service/registered-user.service';

@Component({
  selector: 'app-find-user-by-username',
  templateUrl: './find-user-by-username.component.html',
  styleUrls: ['./find-user-by-username.component.css']
})
export class FindUserByUsernameComponent implements OnInit {

  usernameSearch: string = "";

  constructor(private eRef: ElementRef, 
    private userService: RegisteredUserService,) { }

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
    alert(this.usernameSearch);
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
