import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {FileDetailsComponent} from './file-details.component';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {of} from 'rxjs';

describe('FileDetailsComponent', () => {
  let component: FileDetailsComponent;
  let fixture: ComponentFixture<FileDetailsComponent>;

  beforeEach(async(() => {
    const paramMapStub: ParamMap = {
      get: name => '',
      getAll: name => [],
      has: name => false,
      keys: []
    };
    const activatedRouteStub: Partial<ActivatedRoute> = {
      paramMap: of(paramMapStub)
    };
    TestBed.configureTestingModule({
      declarations: [
        FileDetailsComponent
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
