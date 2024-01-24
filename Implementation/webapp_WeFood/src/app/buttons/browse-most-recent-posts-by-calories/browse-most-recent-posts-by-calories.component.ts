import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { PostDTO, PostDTOInterface } from 'src/app/models/post-dto.model';
import { PostService } from 'src/app/services/post_service/post.service';

@Component({
  selector: 'app-browse-most-recent-posts-by-calories',
  templateUrl: './browse-most-recent-posts-by-calories.component.html',
  styleUrls: ['./browse-most-recent-posts-by-calories.component.css']
})
export class BrowseMostRecentPostsByCaloriesComponent implements OnInit {

  min: number = 0;
  max: number = 10000;
  hours_var: number = 87600;
  limit_var: number = 30; 

  list_of_posts: PostDTOInterface[] = [];

  constructor(private eRef: ElementRef, private postService: PostService) { }

  ngOnInit(): void {
  }

  onMinChange() {
    if (this.min > this.max) {
      this.max = this.min;
    }
  }
  
  onMaxChange() {
    if (this.max < this.min) {
      this.min = this.max;
    }
  }

  activeDropdownIndex: boolean = false;

  toggleDropdown(): void {
    this.activeDropdownIndex = !this.activeDropdownIndex;
  }

  isActiveDropdown(): boolean {
    return this.activeDropdownIndex;
  }

  @HostListener('document:click', ['$event'])
  clickout(event:any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      if (this.isActiveDropdown()) {
        this.toggleDropdown();
      }
    }
  }

    /* USED TO RETURN OUTPUT TO REIGSTERED USER FEED COMPONENT */

  @Output() sendPosts = new EventEmitter<any>();
  @Output() loadPosts = new EventEmitter<any>();
  
  onExecute() {
    // Prepara i dati da inviare
    this.loadPosts.emit();
    this.postService.browseMostRecentPostsByCalories(this.min, this.max, this.hours_var, this.limit_var).subscribe(
      (response) => {
        const dataToEmit = {
          posts: response
        };
        // Emetti l'evento con i dati
        this.sendPosts.emit(dataToEmit);
          },
      (error) => {
        console.log(error);
        this.loadPosts.emit();
      }
    );

  }

}
