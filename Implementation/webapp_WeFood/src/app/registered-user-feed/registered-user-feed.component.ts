import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegisteredUser, RegisteredUserInterface } from '../models/registered-user.model';

@Component({
  selector: 'app-registered-user-feed',
  templateUrl: './registered-user-feed.component.html',
  styleUrls: ['./registered-user-feed.component.css']
})
export class RegisteredUserFeedComponent implements OnInit {
  info: RegisteredUserInterface = new RegisteredUser();
  recipeName: string = "";
  ingredientName: string = "";
  showButtons: boolean = false;
  
  constructor(private router:Router) {
    const navigation = this.router.getCurrentNavigation();
    if(navigation){
      const state = navigation.extras.state as {
        User: RegisteredUserInterface
      };
      this.info = state.User;
    }
  }

  ngOnInit(): void {
  }

}
