import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisteredUserFeedComponent } from './registered-user-feed.component';

describe('RegisteredUserFeedComponent', () => {
  let component: RegisteredUserFeedComponent;
  let fixture: ComponentFixture<RegisteredUserFeedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisteredUserFeedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisteredUserFeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
