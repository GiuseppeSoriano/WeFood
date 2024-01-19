import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { PostDTOInterface } from 'src/app/models/post-dto.model';
import { RegisteredUserInterface } from 'src/app/models/registered-user.model';
import { StarRankingInterface } from 'src/app/models/star-ranking.model';

@Injectable({
  providedIn: 'root'
})
export class StarRankingService {

  constructor(private http: HttpClient) { }

  votePost(info: RegisteredUserInterface, starRanking: StarRankingInterface, postDTO: PostDTOInterface) {
    const requestData = {
      user: info,
      starRanking: starRanking,
      postDTO: postDTO
    };
    return this.http.post<boolean>('http://localhost:8080/starranking/create', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteVote(info: RegisteredUserInterface, starRanking: StarRankingInterface, postDTO: PostDTOInterface) {
    const requestData = {
      user: info,
      starRanking: starRanking,
      postDTO: postDTO
    };
    return this.http.post<boolean>('http://localhost:8080/starranking/delete', requestData)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
