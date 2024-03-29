import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post_service/post.service';

@Component({
  selector: 'app-calories-analysis',
  templateUrl: './calories-analysis.component.html',
  styleUrls: ['./calories-analysis.component.css']
})
export class CaloriesAnalysisComponent implements OnInit {

  showList: boolean = false;      // USED TO HANDLE LIST VISIBILITY
  canCloseList: boolean = true;   // USED TO HANDLE LIST VISIBILITY
  recipeNameSearch: string = "";
  result: number = 0;

  constructor(private eRef: ElementRef, private postService: PostService) { }

  ngOnInit(): void {
  }

  activeDropdownIndex: boolean = false;

  toggleDropdown(): void {
    if(this.showList) {
      this.showList = false;
    }
    else{
      setTimeout(() => {
      }, 300);
      this.activeDropdownIndex = !this.activeDropdownIndex;
    }
  }

  isActiveDropdown(): boolean {
    return this.activeDropdownIndex;
  }

  @HostListener('document:click', ['$event'])
  clickout(event:any) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      if(this.showList) {
        this.showList = false;
      }
    }
  }

  search(recipeNameSearch: string) {
    if(this.recipeNameSearch == "") {
      alert('No recipes was inserted');
    } else {
      this.postService.caloriesAnalysis(recipeNameSearch).subscribe(
        data => {
            this.result = data;
        },
        error => {
          if (error.status === 401) {
            // Gestisci l'errore 401 qui
            alert('No recipes where found');
          }
        }
      );
    }
  }

}
