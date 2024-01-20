import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindNewIngredientsBasedOnFriendsUsageComponent } from './find-new-ingredients-based-on-friends-usage.component';

describe('FindNewIngredientsBasedOnFriendsUsageComponent', () => {
  let component: FindNewIngredientsBasedOnFriendsUsageComponent;
  let fixture: ComponentFixture<FindNewIngredientsBasedOnFriendsUsageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindNewIngredientsBasedOnFriendsUsageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindNewIngredientsBasedOnFriendsUsageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
