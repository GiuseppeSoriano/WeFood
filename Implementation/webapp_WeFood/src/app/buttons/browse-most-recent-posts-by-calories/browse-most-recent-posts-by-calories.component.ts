import { Component, ElementRef, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-browse-most-recent-posts-by-calories',
  templateUrl: './browse-most-recent-posts-by-calories.component.html',
  styleUrls: ['./browse-most-recent-posts-by-calories.component.css']
})
export class BrowseMostRecentPostsByCaloriesComponent implements OnInit {

  min: number = 0;
  max: number = 500;
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
