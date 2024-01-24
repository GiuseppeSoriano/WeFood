import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { PostService } from 'src/app/services/post_service/post.service';

@Component({
  selector: 'app-browse-most-recent-top-rated-posts',
  templateUrl: './browse-most-recent-top-rated-posts.component.html',
  styleUrls: ['./browse-most-recent-top-rated-posts.component.css']
})
export class BrowseMostRecentTopRatedPostsComponent implements OnInit {
  hours_var: number = 87600;
  limit_var: number = 30;
  constructor(private eRef: ElementRef, private postService: PostService) { }

  ngOnInit(): void {
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

  @Output() sendPosts = new EventEmitter<any>();
  @Output() loadPosts = new EventEmitter<any>();
  
  onExecute() {
    // Prepara i dati da inviare
    this.loadPosts.emit();
    this.postService.browseMostRecentTopRatedPosts(this.hours_var, this.limit_var).subscribe(
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
