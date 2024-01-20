import { Component, ElementRef, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-browse-most-recent-top-rated-post-by-ingredients',
  templateUrl: './browse-most-recent-top-rated-post-by-ingredients.component.html',
  styleUrls: ['./browse-most-recent-top-rated-post-by-ingredients.component.css']
})
export class BrowseMostRecentTopRatedPostByIngredientsComponent implements OnInit {
  hours_var: number = 87600;
  limit_var: number = 30;
  constructor(private eRef: ElementRef) { }

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

}
