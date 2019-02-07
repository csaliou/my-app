import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {HttpClientModule} from '@angular/common/http';
import {MatMenuModule} from '@angular/material/menu';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { ChaptersComponent } from './chapters.component';

describe('ChaptersComponent', () => {
  let component: ChaptersComponent;
  let fixture: ComponentFixture<ChaptersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule,MatMenuModule,BrowserAnimationsModule],
      declarations: [ChaptersComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChaptersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
