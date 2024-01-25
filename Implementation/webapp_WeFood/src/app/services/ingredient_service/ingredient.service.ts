import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { RegisteredUserDTO, RegisteredUserDTOInterface } from 'src/app/models/registered-user-dto.model';
import { RegisteredUserInterface } from 'src/app/models/registered-user.model';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {
  ingredients: IngredientInterface[] =[];

  constructor(private http: HttpClient) { }

  initializeIngredients() {
    this.http.post<IngredientInterface[]>('http://localhost:8080/ingredient/getAllIngredients', {}).subscribe(
      data => {
        this.ingredients = data;
        console.log(this.ingredients);
      },
      error => {
        alert('Error in loading page');
      }
    );
  }

  getAllIngredients() {
    return this.ingredients;
  }

  mostPopularCombinationOfIngredients(ingredient_name: string) {
    console.log(ingredient_name);
    return this.http.post<string[]>('http://localhost:8080/ingredient/mostPopularCombinationOfIngredients', ingredient_name)
      .pipe(
        catchError(this.handleError)
      );
  }

  findNewIngredientsBasedOnFriendsUsage(info: RegisteredUserInterface) {
    const registeredUserDTO = new RegisteredUserDTO(info.id, info.username);
    return this.http.post<string[]>('http://localhost:8080/ingredient/findNewIngredientsBasedOnFriendsUsage', registeredUserDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findMostUsedIngredientsByUser(registeredUserDTO: RegisteredUserDTOInterface) {
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
