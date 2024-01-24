import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindUserByUsernameComponent } from './find-user-by-username.component';

describe('FindUserByUsernameComponent', () => {
  let component: FindUserByUsernameComponent;
  let fixture: ComponentFixture<FindUserByUsernameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindUserByUsernameComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindUserByUsernameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
