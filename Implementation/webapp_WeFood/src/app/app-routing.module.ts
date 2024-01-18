import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisteredUserFeedComponent } from './registered-user-feed/registered-user-feed.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'registered-user-feed', component: RegisteredUserFeedComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
