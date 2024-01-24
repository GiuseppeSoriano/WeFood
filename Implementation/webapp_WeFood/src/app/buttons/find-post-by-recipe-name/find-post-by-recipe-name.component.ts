import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { PostDTOInterface } from 'src/app/models/post-dto.model';
import { PostService } from 'src/app/services/post_service/post.service';

@Component({
  selector: 'app-find-post-by-recipe-name',
  templateUrl: './find-post-by-recipe-name.component.html',
  styleUrls: ['./find-post-by-recipe-name.component.css']
})
export class FindPostByRecipeNameComponent implements OnInit {

  recipeNameSearch: string = '';
  isLoading = false;
  list_of_posts: PostDTOInterface[] = [];
  filteredPosts: PostDTOInterface[] = [];

  constructor(private eRef: ElementRef, 
    private postService: PostService,) { }

  ngOnInit(): void {
  }

  activeDropdownIndex: boolean = false;

  toggleDropdown(): void {
    this.activeDropdownIndex = !this.activeDropdownIndex;
  }

  isActiveDropdown(): boolean {
    return this.activeDropdownIndex;
  }

  @Output() sendPosts = new EventEmitter<any>();
  @Output() loadPosts = new EventEmitter<any>();
  
  onExecute() {
    // Prepara i dati da inviare
    this.loadPosts.emit();
    this.postService.findPostsByRecipeName(this.recipeNameSearch).subscribe(
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

  @HostListener('document:click', ['$event'])
  clickout(event:any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      if (this.isActiveDropdown()) {
        this.toggleDropdown();
      }
    }
  }

}
