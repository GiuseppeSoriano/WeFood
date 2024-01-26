import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { RegisteredUserDTO, RegisteredUserDTOInterface } from 'src/app/models/registered-user-dto.model';
import { RegisteredUserPageInterface } from 'src/app/models/registered-user-page.model';
import { RegisteredUser, RegisteredUserInterface } from 'src/app/models/registered-user.model';

@Injectable({
  providedIn: 'root'
})
export class RegisteredUserService {
  info: RegisteredUserInterface = new RegisteredUser();
  usersFollowed: RegisteredUserDTOInterface[] = [];

  constructor(private http: HttpClient) {
    this.loadUserCredentials();
  }

  private loadUserCredentials() {
    const savedInfo = localStorage.getItem('userCredentials');
    if (savedInfo) {
      this.info = JSON.parse(savedInfo);
    }
    const savedFollowed = localStorage.getItem('userFollowed');
    if (savedFollowed) {
      this.usersFollowed = JSON.parse(savedFollowed);
    }
  }

  setCredentials(info: RegisteredUserInterface) {   // USED TO SET THE CREDENTIALS OF THE LOGGED USER WHEN USER REGISTERS
    this.info = info;
    localStorage.setItem('userCredentials', JSON.stringify(info));
  }

  login(username: string, password: string){
    
    return this.http.post<RegisteredUserInterface>('http://localhost:8080/registereduser/login', { username, password }).subscribe(
      data => {
        this.setCredentials(data);
        this.retrieveFollowed();
        console.log(this.info.id);
      },
      error => {
        if (error.status === 401) {
          // Gestisci l'errore 401 qui
          this.info = new RegisteredUser();
        }
      }
    );
  }

  logout() {
    localStorage.removeItem('userCredentials');
    localStorage.removeItem('userFollowed');
    this.info = new RegisteredUser();
  }

  modifyPersonalInformation(info_updated: RegisteredUserInterface) {
    return this.http.post<boolean>('http://localhost:8080/registereduser/modifyPersonalInformation', info_updated)
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteUser() {
    return this.http.post<boolean>('http://localhost:8080/registereduser/deleteUser', this.info)
      .pipe(
        catchError(this.handleError)
      );
  }

  followUser(userToFollow_var: RegisteredUserInterface) {
    const requestData = {
      registeredUserDTO: new RegisteredUserDTO(this.info.id, this.info.username),
      usernameToFollow: userToFollow_var
    };
    return this.http.post<boolean>('http://localhost:8080/registereduser/followUser', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  unfollowUser(userToUnfollow_var: RegisteredUserInterface) {
    const requestData = {
      registeredUserDTO: new RegisteredUserDTO(this.info.id, this.info.username),
      usernameToFollow: userToUnfollow_var
    };
    return this.http.post<boolean>('http://localhost:8080/registereduser/unfollowUser', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  findFriends(registeredUserDTO = new RegisteredUserDTO(this.info.id, this.info.username)) {
    return this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findFriends', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findFollowers(registeredUserDTO = new RegisteredUserDTO(this.info.id, this.info.username)) {
    return this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findFollowers', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  retrieveFollowed(registeredUserDTO = new RegisteredUserDTO(this.info.id, this.info.username)) {
    this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findFollowed', registeredUserDTO).subscribe(
      data => {
        this.usersFollowed = data;
        localStorage.setItem('userFollowed', JSON.stringify(data));
      },
      error => {
        alert('Error in loading followed');
      }
    );
  }

  findFollowed(registeredUserDTO = new RegisteredUserDTO(this.info.id, this.info.username)) {
    return this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findFollowed', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findUsersToFollowBasedOnUserFriends() {
    const registeredUserDTO = new RegisteredUserDTO(this.info.id, this.info.username);
    return this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findUsersToFollowBasedOnUserFriends', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findMostFollowedUsers() {
    return this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findMostFollowedUsers', {})
      .pipe(
        catchError(this.handleError)
      );
  }

  findUsersByIngredientUsage(ingredient: string) {
    return this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findUsersByIngredientUsage', ingredient)
      .pipe(
        catchError(this.handleError)
      );
  }

  findRegisteredUserPageByUsername(username: string) {
    return this.http.post<RegisteredUserPageInterface>('http://localhost:8080/registereduser/findRegisteredUserPageByUsername', username)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
