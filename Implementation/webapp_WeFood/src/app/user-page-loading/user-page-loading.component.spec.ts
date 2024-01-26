import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPageLoadingComponent } from './user-page-loading.component';

describe('UserPageLoadingComponent', () => {
  let component: UserPageLoadingComponent;
  let fixture: ComponentFixture<UserPageLoadingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPageLoadingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserPageLoadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
