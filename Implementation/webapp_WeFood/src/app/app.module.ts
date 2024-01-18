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

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoggingPopupComponent,
    RegisteredUserFeedComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [RegisteredUserService, PostService],
  bootstrap: [AppComponent]
})
export class AppModule { }
