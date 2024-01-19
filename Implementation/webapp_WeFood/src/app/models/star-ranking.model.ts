export interface StarRankingInterface {
    username: string;
    vote: number;
}

export class StarRanking implements StarRankingInterface {
    username: string;
    vote: number;
  
    constructor(username:string = "", vote:number = 0) {
      this.username = username;
      this.vote = vote;
    }
}