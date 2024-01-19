import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegisteredUser, RegisteredUserInterface } from '../models/registered-user.model';
import { MostPopularCombinationOfIngredientsComponent } from '../buttons/most-popular-combination-of-ingredients/most-popular-combination-of-ingredients.component';

@Component({
  selector: 'app-registered-user-feed',
  templateUrl: './registered-user-feed.component.html',
  styleUrls: ['./registered-user-feed.component.css']
})
export class RegisteredUserFeedComponent implements OnInit {
  info: RegisteredUserInterface = new RegisteredUser();
  recipeName: string = "";
  ingredientName: string = "";
  

  
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

  mostPopularCombinationOfIngredients($event: any) {
    throw new Error('Method not implemented.');
  }    

  findUsersByIngredientUsage($event: any) {
    throw new Error('Method not implemented.');
  }
    
}
