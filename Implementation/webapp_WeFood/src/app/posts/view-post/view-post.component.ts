import { Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Comment } from 'src/app/models/comment.model';
import { Post, PostInterface } from 'src/app/models/post.model';
import { Recipe, RecipeInterface } from 'src/app/models/recipe.model';
import { StarRanking } from 'src/app/models/star-ranking.model';
import { PostDTO } from 'src/app/models/post-dto.model';
import { PostService } from 'src/app/services/post_service/post.service';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { CommentService } from 'src/app/services/comment_service/comment.service';
import { StarRankingService } from 'src/app/services/star_ranking_service/star-ranking.service';
import { RegisteredUser, RegisteredUserInterface } from 'src/app/models/registered-user.model';
import { RegisteredUserService } from 'src/app/services/registered_user_service/registered-user.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {
  comments_visible: boolean = false;
  starRankings_visible: boolean = false;

  newCommentText: string = '';
  selectedStar: number = 0;
  ingredients: boolean = false;

  // comment1: Comment = new Comment("giuseppesoriano", "This is a comment", new Date());
  // starRanking1: StarRanking = new StarRanking("giuseppesoriano", 4.5);
  // image_string: string = "";
  // recipe: RecipeInterface = new Recipe("Name of my recipe", this.image_string, ["Step 1", "Step 2"], new Map<string, number>([["Ingredient 1", 1], ["Ingredient 2", 2]]), 100);
  // post: PostInterface = new Post("giuseppesoriano", "This is a description", new Date(), 4.5, [this.comment1], [this.starRanking1], this.recipe);
  
  postDTO: PostDTO = new PostDTO("658572b7d312a33aeb784d22", "", "");
  post: PostInterface = new Post();

  ingredientsList: IngredientInterface[] = [];
  info: RegisteredUserInterface = new RegisteredUser();

  @Output() closePopup: EventEmitter<void> = new EventEmitter();

  constructor(private postService: PostService, private eRef:ElementRef, private commentService: CommentService, private starRankingService: StarRankingService, private userService: RegisteredUserService) { }

  ngOnInit(): void {
    this.postService.findPostByPostDTO(this.postDTO).subscribe((data: PostInterface) => {
      this.post = data;
    });
  }

  showIngredients() {
    this.ingredients = !this.ingredients;
  }

  getUser() {
    return this.userService.info;
  }

  commentPost() {
    const newComment = new Comment(this.getUser().username, this.newCommentText, new Date());
    this.post.comments.push(newComment);
    this.newCommentText = '';  // Clear the input after submitting
    //this.commentService.commentPost(this.post.getUser(), this.postDTO, newComment).subscribe((data: boolean) => {
    //  if(data) {
    //    this.post.comments.push(newComment);
    //    this.newCommentText = '';  // Clear the input after submitting
    //  } else {
    //    alert("Error commenting post");
    //    this.close();
    //  }
    //});
  }

  deleteComment(comment: Comment) {
    // Implementa la logica per eliminare il commento
    this.post.comments = this.post.comments.filter(c => c !== comment);
    //this.commentService.deleteComment(this.getUser(), this.postDTO, comment).subscribe((data: boolean) => {
    //  if(data) {
    //    this.post.comments = this.post.comments.filter(c => c !== comment);
    //  } else {
    //    alert("Error deleting comment");
    //    this.close();
    //  }
    //});
  }

  votePost(star: number) {
    // Implement your logic to submit the star ranking
    this.selectedStar = star;
    const newVote = new StarRanking(this.getUser().username, this.selectedStar);
    this.post.starRankings.push(newVote);
    //this.starRankingService.votePost(this.post.getUser(), starRanking, this.postDTO).subscribe((data: boolean) => {
    //  if(data) {
    //    this.post.starRankings.push(newVote);
    //  } else {
    //    alert("Error voting post");
    //    this.close();
    //  }
    //});
  }

  deleteVote(vote: StarRanking) {
    // Implementa la logica per eliminare il voto dell'utente corrente
    this.post.starRankings = this.post.starRankings.filter(v => v !== vote);
    //this.starRankingService.deleteVote(this.getUser(), vote, this.postDTO).subscribe((data: boolean) => {
    //  if(data) {
    //    this.post.starRankings = this.post.starRankings.filter(v => v !== vote);
    //  } else {
    //    alert("Error deleting vote");
    //    this.close();
    //  }
    //});
  }

  close() {
    this.closePopup.emit();
  }

  @HostListener('document:click', ['$event'])
  clickout(event: any) {
    // Close the popup if clicking outside the popup content
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.close();
    }
  }

  getTimeStamp(timestamp: any) {
    const dt = new Date(timestamp);
    return (dt.getDay()+1).toString().padStart(2, '0')+"/"+(dt.getMonth()+1).toString().padStart(2, '0')+"/"+dt.getFullYear() + ", "+ dt.getHours().toString().padStart(2, '0')+":"+dt.getMinutes().toString().padStart(2, '0');
  }

  toogleComments() {
    this.comments_visible = !this.comments_visible;
  }

  toogleStarRankings() {
    this.starRankings_visible = !this.starRankings_visible;
  }

  canDelete() {
    //return this.getUser().username == this.post.username;
    return true && !this.comments_visible && !this.starRankings_visible;
  }

  deletePost() {
    // this.postService.deletePost(this.post, this.postDTO, this.userService.info).subscribe((data: boolean) => {
    //   if(data) {
    //     this.close();
    //   } else {
    //     alert("Error deleting post");
    //     this.close();
    //   }
    // });
  }
}
