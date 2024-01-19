import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AdminInterface } from 'src/app/models/admin.model';
import { IngredientInterface } from 'src/app/models/ingredient.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.post<AdminInterface>('http://localhost:8080/admin/login', { username, password })
      .pipe(
        catchError(this.handleError)
      );
  }

  createIngredient(ingredient: IngredientInterface){
    return this.http.post<boolean>('http://localhost:8080/admin/createIngredient', ingredient)
      .pipe(
        catchError(this.handleError)
      );
  }

  banUser(username: string){
    return this.http.post<boolean>('http://localhost:8080/admin/banUser', username)
      .pipe(
        catchError(this.handleError)
      );
  }

  unbanUser(username: string){
    return this.http.post<boolean>('http://localhost:8080/admin/unbanUser', username)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
