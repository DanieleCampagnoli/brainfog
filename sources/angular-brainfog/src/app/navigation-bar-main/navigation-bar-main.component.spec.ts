import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationBarMainComponent } from './navigation-bar-main.component';

describe('NavigationBarMainComponent', () => {
  let component: NavigationBarMainComponent;
  let fixture: ComponentFixture<NavigationBarMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavigationBarMainComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationBarMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
