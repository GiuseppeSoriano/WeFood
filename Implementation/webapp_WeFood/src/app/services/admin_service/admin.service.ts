import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Admin, AdminInterface } from 'src/app/models/admin.model';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { RegisteredUserDTOInterface } from 'src/app/models/registered-user-dto.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  info: AdminInterface = new Admin();
  usersBanned: RegisteredUserDTOInterface[] = [];

  constructor(private http: HttpClient) { 
    this.loadAdminCredentials();
  }

  private loadAdminCredentials() {
    const savedInfo = localStorage.getItem('adminCredentials');
    if (savedInfo) {
      this.info = JSON.parse(savedInfo);
    }
    const savedBanned = localStorage.getItem('userBanned');
    if (savedBanned) {
      this.usersBanned = JSON.parse(savedBanned);
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
        this.retrieveBanned();
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
    localStorage.removeItem('userBanned');
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

  retrieveBanned() {
    this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/admin/findBannedUsers', {}).subscribe(
      data => {
        this.usersBanned = data;
        localStorage.setItem('userBanned', JSON.stringify(data));
      },
      error => {
        alert('Error in loading followed');
      }
    );

  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
