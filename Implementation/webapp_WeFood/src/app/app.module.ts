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
import { FindNewIngredientsBasedOnFriendsUsageComponent } from './buttons/find-new-ingredients-based-on-friends-usage/find-new-ingredients-based-on-friends-usage.component';
import { BrowseMostRecentTopRatedPostsComponent } from './buttons/browse-most-recent-top-rated-posts/browse-most-recent-top-rated-posts.component';
import { BrowseMostRecentTopRatedPostByIngredientsComponent } from './buttons/browse-most-recent-top-rated-post-by-ingredients/browse-most-recent-top-rated-post-by-ingredients.component';

import { FilterIngredientPipe } from './pipes/filter-ingredient.pipe';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { UploadPostComponent } from './posts/upload-post/upload-post.component';
import { UserPersonalPageComponent } from './user-personal-page/user-personal-page.component';
import { ViewListOfUsersComponent } from './view-list-of-users/view-list-of-users.component';
import { ViewPostComponent } from './posts/view-post/view-post.component';
import { ModifyPersonalInformationComponent } from './modify-personal-information/modify-personal-information.component';
import { BrowseMostRecentPostsByCaloriesComponent } from './buttons/browse-most-recent-posts-by-calories/browse-most-recent-posts-by-calories.component';
import { FindPostByRecipeNameComponent } from './buttons/find-post-by-recipe-name/find-post-by-recipe-name.component';
import { FindRecipeByIngredientsComponent } from './buttons/find-recipe-by-ingredients/find-recipe-by-ingredients.component';
import { CaloriesAnalysisComponent } from './buttons/calories-analysis/calories-analysis.component';
import { AdminFeedComponent } from './admin-feed/admin-feed.component';
import { FindUserByUsernameComponent } from './buttons/find-user-by-username/find-user-by-username.component';
import { UserPageComponent } from './user-page/user-page.component';
import { UserPageLoadingComponent } from './user-page-loading/user-page-loading.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoggingPopupComponent,
    RegisteredUserFeedComponent,
    MostPopularCombinationOfIngredientsComponent,
    FindUsersByIngredientUsageComponent,
    FindNewIngredientsBasedOnFriendsUsageComponent,
    BrowseMostRecentTopRatedPostsComponent,
    BrowseMostRecentTopRatedPostByIngredientsComponent,
    FilterIngredientPipe,
    AdminDashboardComponent,
    UploadPostComponent,
    UserPersonalPageComponent,
    ViewListOfUsersComponent,
    ViewPostComponent,
    ModifyPersonalInformationComponent,
    BrowseMostRecentPostsByCaloriesComponent,
    FindPostByRecipeNameComponent,
    FindRecipeByIngredientsComponent,
    CaloriesAnalysisComponent,
    AdminFeedComponent,
    FindUserByUsernameComponent,
    UserPageComponent,
    UserPageLoadingComponent
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
