import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindPostByRecipeNameComponent } from './find-post-by-recipe-name.component';

describe('FindPostByRecipeNameComponent', () => {
  let component: FindPostByRecipeNameComponent;
  let fixture: ComponentFixture<FindPostByRecipeNameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindPostByRecipeNameComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindPostByRecipeNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
