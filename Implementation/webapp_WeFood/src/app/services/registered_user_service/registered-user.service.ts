import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { RegisteredUserDTO, RegisteredUserDTOInterface } from 'src/app/models/registered-user-dto.model';
import { RegisteredUserPageInterface } from 'src/app/models/registered-user-page.model';
import { RegisteredUser, RegisteredUserInterface } from 'src/app/models/registered-user.model';

@Injectable({
  providedIn: 'root'
})
export class RegisteredUserService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.post<RegisteredUserInterface>('http://localhost:8080/registereduser/login', { username, password })
      .pipe(
        catchError(this.handleError)
      );
  }

  modifyPersonalInformation(info: RegisteredUserInterface) {
    return this.http.post<boolean>('http://localhost:8080/registereduser/modifyPersonalInformation', info)
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteUser(info: RegisteredUserInterface) {
    return this.http.post<boolean>('http://localhost:8080/registereduser/deleteUser', info)
      .pipe(
        catchError(this.handleError)
      );
  }

  followUser(info: RegisteredUserInterface, userToFollow_var: RegisteredUserInterface) {
    const requestData = {
      registeredUserDTO: new RegisteredUserDTO(info._id, info.username),
      usernameToFollow: userToFollow_var
    };
    return this.http.post<boolean>('http://localhost:8080/registereduser/followUser', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  unfollowUser(info: RegisteredUserInterface, userToUnfollow_var: RegisteredUserInterface) {
    const requestData = {
      registeredUserDTO: new RegisteredUserDTO(info._id, info.username),
      usernameToFollow: userToUnfollow_var
    };
    return this.http.post<boolean>('http://localhost:8080/registereduser/unfollowUser', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  findFriends(info: RegisteredUserInterface) {
    const registeredUserDTO = new RegisteredUserDTO(info._id, info.username);
    return this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findFriends', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findFollowers(info: RegisteredUserInterface) {
    const registeredUserDTO = new RegisteredUserDTO(info._id, info.username);
    return this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findFollowers', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findFollowed(info: RegisteredUserInterface) {
    const registeredUserDTO = new RegisteredUserDTO(info._id, info.username);
    return this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/registereduser/findFollowed', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findUsersToFollowBasedOnUserFriends(info: RegisteredUserInterface) {
    const registeredUserDTO = new RegisteredUserDTO(info._id, info.username);
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
