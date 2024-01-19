import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { RegisteredUserInterface } from 'src/app/models/registered-user.model';

@Injectable({
  providedIn: 'root'
})
export class UnregisteredUserService {

  constructor(private http: HttpClient) { }

  register(username_var: string, password_var: string, name_var: string, surname_var: string) {
    return this.http.post<RegisteredUserInterface>('http://localhost:8080/unregistereduser/register', { name: name_var, surname: surname_var, username: username_var, password: password_var })
      .pipe(
        catchError(this.handleError)
      );
  } 

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
