import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { PostDTOInterface } from 'src/app/models/post-dto.model';
import { PostInterface } from 'src/app/models/post.model';
import { RegisteredUserInterface } from 'src/app/models/registered-user.model';
import { RegisteredUserService } from '../registered_user_service/registered-user.service';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient, private userService: RegisteredUserService) { }

  uploadPost(post: PostInterface, postDTO: PostDTOInterface, info: RegisteredUserInterface) {
    const postCopy = JSON.parse(JSON.stringify(post));
    // Converte il Map in un oggetto semplice
    postCopy.recipe.ingredients = Object.fromEntries(post.recipe.ingredients);
    const requestData = {
        post: postCopy,
        postDTO: postDTO,
        user: info
      };
    return this.http.post<boolean>('http://localhost:8080/post/uploadPost', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  modifyPost(post: PostInterface, postDTO: PostDTOInterface, info: RegisteredUserInterface) {
    const requestData = {
      post: post,
      postDTO: postDTO,
      user: info
    };
    return this.http.post<boolean>('http://localhost:8080/post/modifyPost', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  deletePost(post: PostInterface, postDTO: PostDTOInterface) { 
    const requestData = {
      post: post,
      postDTO: postDTO,
      user: this.userService.info // value isn't used, but it's necessary to pass a value
    };
    return this.http.post<boolean>('http://localhost:8080/post/deletePost', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  browseMostRecentTopRatedPosts(hours_var: number = 87600, limit_var: number = 30) {
    return this.http.post<PostDTOInterface[]>('http://localhost:8080/post/browseMostRecentTopRatedPosts', {hours: hours_var, limit: limit_var})
      .pipe(
        catchError(this.handleError)
      );
  }

  browseMostRecentTopRatedPostsByIngredients(ingredientNames: string[], hours: number, limit: number){
    if(ingredientNames.length == 0) return this.browseMostRecentTopRatedPosts(hours, limit);
    const requestData = {
      ingredientNames: ingredientNames,
      hours: hours,
      limit: limit
    };
    return this.http.post<PostDTOInterface[]>('http://localhost:8080/post/browseMostRecentTopRatedPostsByIngredients', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  browseMostRecentPostsByCalories(minCalories: number, maxCalories: number, hours: number, limit: number){
    const requestData = {
      minCalories: minCalories,
      maxCalories: maxCalories,
      hours: hours,
      limit: limit
    };
    return this.http.post<PostDTOInterface[]>('http://localhost:8080/post/browseMostRecentPostsByCalories', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  findPostByPostDTO(postDTO: PostDTOInterface){
    return this.http.post<PostInterface>('http://localhost:8080/post/findPostByPostDTO', postDTO)
      .pipe(
        catchError(this.handleError)
      );
  }

  findPostsByRecipeName(recipeName: string){
    return this.http.post<PostDTOInterface[]>('http://localhost:8080/post/findPostsByRecipeName', recipeName)
      .pipe(
        catchError(this.handleError)
      );
  }

  interactionsAnalysis(){
    return this.http.post<Map<string, number>>('http://localhost:8080/post/interactionsAnalysis', {})
      .pipe(
        catchError(this.handleError)
      );
  }

  userInteractionsAnalysis(username: string){
    return this.http.post<Map<string, number>>('http://localhost:8080/post/userInteractionsAnalysis', username)
      .pipe(
        catchError(this.handleError)
      );
  }

  caloriesAnalysis(recipeName: string){
    return this.http.post<number>('http://localhost:8080/post/caloriesAnalysis', recipeName)
      .pipe(
        catchError(this.handleError)
      );
  }

  averageTotalCaloriesByUser(username: string){
    return this.http.post<number>('http://localhost:8080/post/averageTotalCaloriesByUser', username)
      .pipe(
        catchError(this.handleError)
      );
  }

  findRecipeByIngredients(ingredientNames: string[]){
    return this.http.post<PostDTOInterface[]>('http://localhost:8080/post/findRecipeByIngredients', ingredientNames)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
