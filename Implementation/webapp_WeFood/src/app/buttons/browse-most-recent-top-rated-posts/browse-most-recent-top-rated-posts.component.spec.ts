import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseMostRecentTopRatedPostsComponent } from './browse-most-recent-top-rated-posts.component';

describe('BrowseMostRecentTopRatedPostsComponent', () => {
  let component: BrowseMostRecentTopRatedPostsComponent;
  let fixture: ComponentFixture<BrowseMostRecentTopRatedPostsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BrowseMostRecentTopRatedPostsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BrowseMostRecentTopRatedPostsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
