import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';

import{HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { LoggingPopupComponent } from './logging-popup/logging-popup.component';
import { RegisteredUserFeedComponent } from './registered-user-feed/registered-user-feed.component';
import { RegisteredUserService } from './services/registered_user_service/registered-user.service';
import { PostService } from './services/post_service/post.service';
import { MostPopularCombinationOfIngredientsComponent } from './buttons/most-popular-combination-of-ingredients/most-popular-combination-of-ingredients.component';
import { CommonModule } from '@angular/common';
import { FindUsersByIngredientUsageComponent } from './buttons/find-users-by-ingredient-usage/find-users-by-ingredient-usage.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoggingPopupComponent,
    RegisteredUserFeedComponent,
    MostPopularCombinationOfIngredientsComponent,
    FindUsersByIngredientUsageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CommonModule
  ],
  providers: [RegisteredUserService, PostService],
  bootstrap: [AppComponent]
})
export class AppModule { }
