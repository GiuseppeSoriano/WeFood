import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { IngredientService } from 'src/app/services/ingredient_service/ingredient.service';
import { RegisteredUserService } from 'src/app/services/registered_user_service/registered-user.service';

@Component({
  selector: 'app-find-new-ingredients-based-on-friends-usage',
  templateUrl: './find-new-ingredients-based-on-friends-usage.component.html',
  styleUrls: ['./find-new-ingredients-based-on-friends-usage.component.css']
})
export class FindNewIngredientsBasedOnFriendsUsageComponent implements OnInit {
  suggestions: string[] = [];

  constructor(private eRef: ElementRef, private ingredientService: IngredientService, private registeredUserService: RegisteredUserService) { }

  ngOnInit(): void {
  }

  execute() {
    if(!this.isActiveDropdown()){
      this.ingredientService.findNewIngredientsBasedOnFriendsUsage(this.registeredUserService.info).subscribe(
        data => {
          this.suggestions = ["No suggestions available"];
          if (data.length > 0)
            this.suggestions = data;
        },
        error => {
          console.log(error);
        }
      );
    }
    this.toggleDropdown();
  }

  activeDropdownIndex: boolean = false;

  toggleDropdown(): void {
    if (this.suggestions.length > 0)
      setTimeout(() => {
        this.suggestions = [];
      }, 300);
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
