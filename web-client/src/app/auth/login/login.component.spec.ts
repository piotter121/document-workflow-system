import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LoginComponent} from './login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from '../auth.service';
import {Pipe, PipeTransform} from '@angular/core';
import {InvalidFeedbackComponent} from '../../shared/invalid-feedback/invalid-feedback.component';
import {Router} from '@angular/router';
import {ToastNotificationService} from '../../shared/toast-notification.service';

@Pipe({
  name: 'translate'
})
class TranslatePipeStub implements PipeTransform {
  transform(value: any, ...args: any[]): any {
  }
}

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  let routerSpy: { navigateByUrl: jasmine.Spy };

  beforeEach(async(() => {
    let authServiceStub: Partial<AuthService> = {};
    routerSpy = jasmine.createSpyObj('Router', ['navigateByUrl']);
    let toastNotificationStub: Partial<ToastNotificationService> = {};

    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        ReactiveFormsModule
      ],
      declarations: [
        LoginComponent,
        TranslatePipeStub,
        InvalidFeedbackComponent
      ],
      providers: [
        {provide: AuthService, useValue: authServiceStub},
        {provide: Router, useValue: routerSpy},
        {provide: ToastNotificationService, useValue: toastNotificationStub}
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
