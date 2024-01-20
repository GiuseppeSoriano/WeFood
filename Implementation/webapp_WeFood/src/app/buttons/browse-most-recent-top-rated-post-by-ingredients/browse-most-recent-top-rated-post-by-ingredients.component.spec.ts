import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseMostRecentTopRatedPostByIngredientsComponent } from './browse-most-recent-top-rated-post-by-ingredients.component';

describe('BrowseMostRecentTopRatedPostByIngredientsComponent', () => {
  let component: BrowseMostRecentTopRatedPostByIngredientsComponent;
  let fixture: ComponentFixture<BrowseMostRecentTopRatedPostByIngredientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BrowseMostRecentTopRatedPostByIngredientsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BrowseMostRecentTopRatedPostByIngredientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
