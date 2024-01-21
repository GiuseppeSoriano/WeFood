import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPersonalPageComponent } from './user-personal-page.component';

describe('UserPersonalPageComponent', () => {
  let component: UserPersonalPageComponent;
  let fixture: ComponentFixture<UserPersonalPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPersonalPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserPersonalPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
