import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { RegisteredUserDTO } from 'src/app/models/registered-user-dto.model';
import { RegisteredUserInterface } from 'src/app/models/registered-user.model';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  constructor(private http: HttpClient) { }

  getAllIngredients() {
    return this.http.post<IngredientInterface[]>('http://localhost:8080/ingredient/getAllIngredients', {})
      .pipe(
        catchError(this.handleError)
      );
  }

  mostPopularCombinationsOfIngredients(ingredient_name: string) {
    return this.http.post<string[]>('http://localhost:8080/ingredient/mostPopularCombinationsOfIngredients', {ingredient_name})
      .pipe(
        catchError(this.handleError)
      );
  }

  findNewIngredientsBasedOnFriendsUsage(info: RegisteredUserInterface) {
    const registeredUserDTO = new RegisteredUserDTO(info._id, info.username);
    return this.http.post<string[]>('http://localhost:8080/ingredient/findNewIngredientsBasedOnFriendsUsage', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findMostUsedIngredientsByUser(info: RegisteredUserInterface) {
    const registeredUserDTO = new RegisteredUserDTO(info._id, info.username);
    return this.http.post<string[]>('http://localhost:8080/ingredient/findMostUsedIngredientsByUser', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findMostLeastUsedIngredients(criteria: boolean) {
    return this.http.post<string[]>('http://localhost:8080/ingredient/findMostLeastUsedIngredients', criteria)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
