import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoggingPopupComponent } from './logging-popup.component';

describe('LoggingPopupComponent', () => {
  let component: LoggingPopupComponent;
  let fixture: ComponentFixture<LoggingPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoggingPopupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoggingPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
