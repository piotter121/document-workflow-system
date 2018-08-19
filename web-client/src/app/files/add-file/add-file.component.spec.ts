import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddFileComponent} from './add-file.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Component, Input} from '@angular/core';
import {FilesService} from '../files.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastNotificationService} from '../../shared/toast-notification.service';

@Component({
  selector: 'invalid-feedback',
  template: ''
})
class InvalidFeedbackStubComponent {
  @Input() control: any;
}

describe('AddFileComponent', () => {
  let component: AddFileComponent;
  let fixture: ComponentFixture<AddFileComponent>;

  beforeEach(async(() => {
    const filesServiceStub: Partial<FilesService> = {};
    const activatedRouteStub: Partial<ActivatedRoute> = {};
    const routerStub: Partial<Router> = {};
    const toastNotificationStub: Partial<ToastNotificationService> = {};
    TestBed.configureTestingModule({
      declarations: [
        AddFileComponent,
        InvalidFeedbackStubComponent
      ],
      imports: [
        FormsModule,
        ReactiveFormsModule
      ],
      providers: [
        {provide: FilesService, useValue: filesServiceStub},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        {provide: Router, useValue: routerStub},
        {provide: ToastNotificationService, useValue: toastNotificationStub}
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
