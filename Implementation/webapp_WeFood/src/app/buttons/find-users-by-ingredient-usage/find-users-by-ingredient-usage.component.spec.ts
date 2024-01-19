import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindUsersByIngredientUsageComponent } from './find-users-by-ingredient-usage.component';

describe('FindUsersByIngredientUsageComponent', () => {
  let component: FindUsersByIngredientUsageComponent;
  let fixture: ComponentFixture<FindUsersByIngredientUsageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindUsersByIngredientUsageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindUsersByIngredientUsageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
