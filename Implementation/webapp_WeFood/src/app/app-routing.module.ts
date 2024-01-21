import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisteredUserFeedComponent } from './registered-user-feed/registered-user-feed.component';
import { MostPopularCombinationOfIngredientsComponent } from './buttons/most-popular-combination-of-ingredients/most-popular-combination-of-ingredients.component';
import { FindUsersByIngredientUsageComponent } from './buttons/find-users-by-ingredient-usage/find-users-by-ingredient-usage.component';
import { FindNewIngredientsBasedOnFriendsUsageComponent } from './buttons/find-new-ingredients-based-on-friends-usage/find-new-ingredients-based-on-friends-usage.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { BrowseMostRecentTopRatedPostsComponent } from './buttons/browse-most-recent-top-rated-posts/browse-most-recent-top-rated-posts.component';
import { BrowseMostRecentTopRatedPostByIngredientsComponent } from './buttons/browse-most-recent-top-rated-post-by-ingredients/browse-most-recent-top-rated-post-by-ingredients.component';
import { UploadPostComponent } from './posts/upload-post/upload-post.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'registered-user-feed', component: RegisteredUserFeedComponent },
  { path: 'most-popular-combination-of-ingredients', component: MostPopularCombinationOfIngredientsComponent },
  { path: 'find-users-by-ingredient-usage', component: FindUsersByIngredientUsageComponent },
  { path: 'find-new-ingredients-based-on-friends-usage', component: FindNewIngredientsBasedOnFriendsUsageComponent },
  { path: 'browse-most-recent-top-rated-posts', component: BrowseMostRecentTopRatedPostsComponent },
  { path: 'browse-most-recent-top-rated-post-by-ingredients', component: BrowseMostRecentTopRatedPostByIngredientsComponent },
  { path: 'admin-dashboard', component: AdminDashboardComponent},
  { path: 'upload-post', component: UploadPostComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
