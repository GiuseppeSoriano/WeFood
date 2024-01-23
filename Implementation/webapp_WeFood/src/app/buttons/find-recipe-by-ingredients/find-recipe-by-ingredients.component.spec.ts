import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindRecipeByIngredientsComponent } from './find-recipe-by-ingredients.component';

describe('FindRecipeByIngredientsComponent', () => {
  let component: FindRecipeByIngredientsComponent;
  let fixture: ComponentFixture<FindRecipeByIngredientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindRecipeByIngredientsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindRecipeByIngredientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
