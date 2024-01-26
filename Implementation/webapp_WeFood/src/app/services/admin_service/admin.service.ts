import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Admin, AdminInterface } from 'src/app/models/admin.model';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { RegisteredUserDTOInterface } from 'src/app/models/registered-user-dto.model';
import { RegisteredUserPageInterface } from 'src/app/models/registered-user-page.model';

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
        console.log(data);
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

  banUser(user: RegisteredUserDTOInterface){
    this.http.post<boolean>('http://localhost:8080/admin/banUser', user.username).subscribe(
      data => {
        if(data){
          this.usersBanned.push(user);
          localStorage.setItem('userBanned', JSON.stringify(this.usersBanned));
        }
        else{
          alert('Error in banning user');
        }
      },
      error => {
        alert('Error in loading followed');
      }
    );
  }

  unbanUser(user: RegisteredUserDTOInterface){
    this.http.post<boolean>('http://localhost:8080/admin/unbanUser', user.username).subscribe(
      data => {
        if(data){
          this.usersBanned.splice(this.usersBanned.indexOf(user), 1);
          localStorage.setItem('userBanned', JSON.stringify(this.usersBanned));
        }
        else{
          alert('Error in unbanning user');
        }
      },
      error => {
        alert('Error in loading followed');
      }
    );
  }

  retrieveBanned() {
    this.http.post<RegisteredUserDTOInterface[]>('http://localhost:8080/admin/findBannedUsers', {}).subscribe(
      data => {
        console.log("CIAO");
        console.log(data);
        if(data){
          console.log(data); 
          this.usersBanned = data;
        }
        else this.usersBanned = [];
//        this.usersBanned;
        localStorage.setItem('userBanned', JSON.stringify(this.usersBanned));
      },
      error => {
        alert('Error in loading followed');
      }
    );

  }

  adminFindRegisteredUserPageByUsername(username: string) {
    return this.http.post<RegisteredUserPageInterface>('http://localhost:8080/admin/adminFindRegisteredUserPageByUsername', username)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
