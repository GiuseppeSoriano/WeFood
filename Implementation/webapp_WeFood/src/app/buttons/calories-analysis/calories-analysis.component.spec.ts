import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaloriesAnalysisComponent } from './calories-analysis.component';

describe('CaloriesAnalysisComponent', () => {
  let component: CaloriesAnalysisComponent;
  let fixture: ComponentFixture<CaloriesAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CaloriesAnalysisComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaloriesAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
