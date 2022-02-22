import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NasaApodVoronoiComponent } from './nasa-apod-voronoi.component';

describe('NasaApodVoronoiComponent', () => {
  let component: NasaApodVoronoiComponent;
  let fixture: ComponentFixture<NasaApodVoronoiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NasaApodVoronoiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NasaApodVoronoiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
