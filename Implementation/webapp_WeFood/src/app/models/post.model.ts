import { CommentInterface } from "./comment.model";
import { Recipe, RecipeInterface } from "./recipe.model";
import { StarRankingInterface } from "./star-ranking.model";

export interface PostInterface {
    username: string;
    description: string;
    timestamp: Date;
    avgStarRanking: number;
    comments: CommentInterface[];
    starRankings: StarRankingInterface[];
    recipe: RecipeInterface;
}

export class Post implements PostInterface {
    username: string;
    description: string;
    timestamp: Date;
    avgStarRanking: number;
    comments: CommentInterface[];
    starRankings: StarRankingInterface[];
    recipe: RecipeInterface;
  
    constructor(username:string = "", description:string = "", timestamp:Date = new Date(), avgStarRanking:number = 0, comments:CommentInterface[] = [], starRankings:StarRankingInterface[] = [], recipe:RecipeInterface = new Recipe()) {
        this.username = username;
        this.description = description;
        this.timestamp = timestamp;
        this.avgStarRanking = avgStarRanking;
        this.comments = comments;
        this.starRankings = starRankings;
        this.recipe = recipe;
    }

}