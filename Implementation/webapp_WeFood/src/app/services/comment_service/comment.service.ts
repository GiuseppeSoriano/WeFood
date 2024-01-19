import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { CommentInterface } from 'src/app/models/comment.model';
import { PostDTOInterface } from 'src/app/models/post-dto.model';
import { RegisteredUserInterface } from 'src/app/models/registered-user.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  commentPost(info: RegisteredUserInterface, postDTO: PostDTOInterface, comment: CommentInterface) {
    return this.http.post<boolean>('http://localhost:8080/comment/create', { user: info, comment: comment, postDTO: postDTO })
      .pipe(
        catchError(this.handleError)
      );
  }

  updateComment(info: RegisteredUserInterface, postDTO: PostDTOInterface, comment: CommentInterface) {
    return this.http.post<boolean>('http://localhost:8080/comment/update', { user: info, comment: comment, postDTO: postDTO })
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteComment(info: RegisteredUserInterface, postDTO: PostDTOInterface, comment: CommentInterface) {
    return this.http.post<boolean>('http://localhost:8080/comment/delete', { user: info, comment: comment, postDTO: postDTO })
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
