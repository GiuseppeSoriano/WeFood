import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MostPopularCombinationOfIngredientsComponent } from './most-popular-combination-of-ingredients.component';

describe('MostPopularCombinationOfIngredientsComponent', () => {
  let component: MostPopularCombinationOfIngredientsComponent;
  let fixture: ComponentFixture<MostPopularCombinationOfIngredientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MostPopularCombinationOfIngredientsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MostPopularCombinationOfIngredientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
