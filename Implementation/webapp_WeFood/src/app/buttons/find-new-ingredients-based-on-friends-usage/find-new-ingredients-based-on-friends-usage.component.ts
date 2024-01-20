import { Component, ElementRef, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-find-new-ingredients-based-on-friends-usage',
  templateUrl: './find-new-ingredients-based-on-friends-usage.component.html',
  styleUrls: ['./find-new-ingredients-based-on-friends-usage.component.css']
})
export class FindNewIngredientsBasedOnFriendsUsageComponent implements OnInit {


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
