import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RegisterComponent} from './register.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Component, Input, Pipe, PipeTransform} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {AppValidatorsService} from '../../shared/app-validators.service';
import {ToastNotificationService} from '../../shared/toast-notification.service';
import {of} from 'rxjs';

@Component({
  selector: 'invalid-feedback',
  template: ''
})
class InvalidFeedbackStubComponent {
  @Input() control: any;
}

@Pipe({name: 'translate'})
class TranslatePipeStub implements PipeTransform {
  transform(value: any, ...args: any[]): any {
  }
}

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async(() => {
    const authServiceStub: Partial<AuthService> = {};
    const routerStub: Partial<Router> = {};
    const appValidatorsStub: Partial<AppValidatorsService> = {
      nonExistingUserEmail: () => () => of(null)
    };
    const toastNotificationStub: Partial<ToastNotificationService> = {};
    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        ReactiveFormsModule
      ],
      declarations: [
        RegisterComponent,
        InvalidFeedbackStubComponent,
        TranslatePipeStub
      ],
      providers: [
        {provide: AuthService, useValue: authServiceStub},
        {provide: Router, useValue: routerStub},
        {provide: AppValidatorsService, useValue: appValidatorsStub},
        {provide: ToastNotificationService, useValue: toastNotificationStub}
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
