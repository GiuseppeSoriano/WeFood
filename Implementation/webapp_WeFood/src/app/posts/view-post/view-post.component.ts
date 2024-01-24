import { Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Comment, CommentInterface } from 'src/app/models/comment.model';
import { Post, PostInterface } from 'src/app/models/post.model';
import { Recipe, RecipeInterface } from 'src/app/models/recipe.model';
import { StarRanking, StarRankingInterface } from 'src/app/models/star-ranking.model';
import { PostDTO } from 'src/app/models/post-dto.model';
import { PostService } from 'src/app/services/post_service/post.service';
import { IngredientInterface } from 'src/app/models/ingredient.model';
import { CommentService } from 'src/app/services/comment_service/comment.service';
import { StarRankingService } from 'src/app/services/star_ranking_service/star-ranking.service';
import { RegisteredUser, RegisteredUserInterface } from 'src/app/models/registered-user.model';
import { RegisteredUserService } from 'src/app/services/registered_user_service/registered-user.service';
import { AdminService } from 'src/app/services/admin_service/admin.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  canClose: boolean = true;

  editingDescription: boolean = false;

  comments_visible: boolean = false;
  starRankings_visible: boolean = false;

  newCommentText: string = '';
  selectedStar: number = 0;
  ingredients: boolean = false;
  
  @Input() postDTO: PostDTO = new PostDTO();
  post: PostInterface = new Post();

  ingredientsList: IngredientInterface[] = [];
  info: RegisteredUserInterface = new RegisteredUser();

  @Output() closePost: EventEmitter<void> = new EventEmitter();

  personalVote: StarRankingInterface = new StarRanking();

  constructor(private postService: PostService, private eRef:ElementRef, private commentService: CommentService, private starRankingService: StarRankingService, private userService: RegisteredUserService, private adminService: AdminService) { }

  ngOnInit(): void {
    this.postService.findPostByPostDTO(this.postDTO).subscribe((data: PostInterface) => {
      this.post = data;
      // this.post.starRankings.find(v => v.username == this.getUser().username) ? this.personalVote = this.post.starRankings.find(v => v.username == this.getUser().username) : this.personalVote = new StarRanking();
      for(let i = 0; i < this.post.starRankings.length; i++) {
        if(this.post.starRankings[i].username == this.getUser().username) {
          this.personalVote = this.post.starRankings[i];
          this.selectedStar = this.personalVote.vote;
          break;
        }
      }
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
    this.commentService.commentPost(this.getUser(), this.postDTO, newComment).subscribe((data: boolean) => {
     if(data) {
       this.post.comments.push(newComment);
       this.newCommentText = '';  // Clear the input after submitting
     } else {
       alert("Error commenting post");
       this.close();
     }
    });
  }

  deleteComment(comment: Comment) {
    this.commentService.deleteComment(this.getUser(), this.postDTO, comment).subscribe((data: boolean) => {
     if(data) {
       this.post.comments = this.post.comments.filter(c => c !== comment);
     } else {
       alert("Error deleting comment");
       this.close();
     }
    });
  }

  canDeleteComment(comment: Comment){
    return this.getUser().username == this.post.username || this.getUser().username == comment.username || this.adminService.info.username !== '';
  }

  votePost(star: number) {
    // Implement your logic to submit the star ranking
    if(this.personalVote.vote !== 0)
      return;
    const starRanking = new StarRanking(this.getUser().username, star);
    this.starRankingService.votePost(this.getUser(), starRanking, this.postDTO, this.post).subscribe((data: boolean) => {
     if(data) {
      this.personalVote = starRanking;
      this.selectedStar = star;
      this.post.starRankings.push(this.personalVote);
     } else {
       alert("Error voting post");
       this.close();
     }
    });
  }

  deleteVote() {
    // Implementa la logica per eliminare il voto dell'utente corrente
    this.starRankingService.deleteVote(this.getUser(), this.personalVote, this.postDTO, this.post).subscribe((data: boolean) => {
     if(data) {
      this.post.starRankings = this.post.starRankings.filter(v => v !== this.personalVote);
      this.personalVote = new StarRanking();
      this.selectedStar = 0;
     } else {
       alert("Error deleting vote");
       this.close();
     }
    });
  }

  close() {
    this.closePost.emit();
  }

  @HostListener('document:click', ['$event'])
  clickout(event: any) {
    // Close the popup if clicking outside the popup content
    if (!this.eRef.nativeElement.contains(event.target) && !this.canClose) {
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
    return this.getUser().username == this.post.username && !this.comments_visible && !this.starRankings_visible;
  }

  deletePost() {
    this.postService.deletePost(this.post, this.postDTO, this.userService.info).subscribe((data: boolean) => {
      if(data) {
        this.close();
      } else {
        alert("Error deleting post");
        this.close();
      }
    });
  }

  startModifyDescription() {
    this.canClose = false;
    setTimeout(() => {
      this.editingDescription = !this.editingDescription;
      this.canClose = true;
    }, 100);
  }

  modifyDescription() {
    this.canClose = false;
    this.postService.modifyPost(this.post, this.postDTO, this.getUser()).subscribe((data: boolean) => {
      if(data) {
        // NOTHING
      } else {
        alert("Error modifying post");
        this.canClose = true;
        this.close();
      }
    }
    );
    setTimeout(() => {
      this.editingDescription = !this.editingDescription;
      this.canClose = true;
    }, 100);
  }

  editingComment: CommentInterface = new Comment();

  startModifyComment(comment: CommentInterface) {
    this.canClose = false;
    setTimeout(() => {
      this.editingComment = comment;
      this.canClose = true;
    }, 100);
  }

  modifyComment(comment: CommentInterface) {
    this.canClose = false;

    this.commentService.updateComment(this.getUser(), this.postDTO, comment).subscribe((data: boolean) => {
      if(data) {
        this.editingComment = new Comment();
      } else {
        alert("Error modifying comment");
        this.close();
      }
    });

    setTimeout(() => {
      this.editingComment = new Comment();
      this.canClose = true;
    }, 100);
  }
  
  isCommentBeingModified(comment: CommentInterface) {
    return this.editingComment == comment;
  }

}
