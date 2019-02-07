import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VideoComponent } from './video.component';
import { ChaptersComponent } from '../chapters/chapters.component';

describe('VideoComponent', () => {
  let component: VideoComponent;
  let fixture: ComponentFixture<VideoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VideoComponent, ChaptersComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VideoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain a video tag', () => {
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector("video")).toBeTruthy();
  });

  it('video should have all sources', () => {
    const compiled = fixture.debugElement.nativeElement;
    const sources = compiled.querySelector("video").querySelectorAll("source");
    sources.forEach(function (currentValue, currentIndex, listObj) {
      expect(currentValue.type).toMatch(/video\/mp4|video\/ogg/);
    });
  });


});
