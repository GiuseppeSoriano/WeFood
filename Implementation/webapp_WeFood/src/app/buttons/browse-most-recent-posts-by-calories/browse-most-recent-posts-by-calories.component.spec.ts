import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseMostRecentPostsByCaloriesComponent } from './browse-most-recent-posts-by-calories.component';

describe('BrowseMostRecentPostsByCaloriesComponent', () => {
  let component: BrowseMostRecentPostsByCaloriesComponent;
  let fixture: ComponentFixture<BrowseMostRecentPostsByCaloriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BrowseMostRecentPostsByCaloriesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BrowseMostRecentPostsByCaloriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
