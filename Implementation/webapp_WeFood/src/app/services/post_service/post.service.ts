import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { PostDTOInterface } from 'src/app/models/post-dto.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  browseMostRecentTopRatedPosts() {
    return this.http.post<PostDTOInterface[]>('http://localhost:8080/post/browseMostRecentTopRatedPosts', {hours: 87600, limit: 30})
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
