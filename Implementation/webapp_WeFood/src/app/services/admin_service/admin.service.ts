import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Admin, AdminInterface } from 'src/app/models/admin.model';
import { IngredientInterface } from 'src/app/models/ingredient.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  info: AdminInterface = new Admin();

  constructor(private http: HttpClient) { 
    this.loadAdminCredentials();
  }

  private loadAdminCredentials() {
    const savedInfo = localStorage.getItem('adminCredentials');
    if (savedInfo) {
      this.info = JSON.parse(savedInfo);
    }
  }
  
  setCredentials(info: AdminInterface) { 
    this.info = info;
    localStorage.setItem('adminCredentials', JSON.stringify(info));
  }


  login(username: string, password: string) {
    return this.http.post<AdminInterface>('http://localhost:8080/admin/login', { username, password }).subscribe(
      data => {
        this.setCredentials(data);
      },
      error => {
        if (error.status === 401) {
          // Error handling
          this.info = new Admin();
        }
      }
    )
  }

  logout() {
    localStorage.removeItem('adminCredentials');
    this.info = new Admin();
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
