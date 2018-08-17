import {async, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {AuthService} from './auth/auth.service';
import {TranslateService} from '@ngx-translate/core';
import {of} from 'rxjs';
import {Component} from '@angular/core';

@Component({
  selector: 'app-navbar',
  template: ''
})
class NavbarStubComponent {
}

@Component({
  selector: 'router-outlet',
  template: ''
})
class RouterOutletStubComponent {
}

describe('AppComponent', () => {
  beforeEach(async(() => {
    const authServiceStub: Partial<AuthService> = {
      isAuthenticated$: of(true)
    };
    const translateServiceStub: Partial<TranslateService> = {
      use: lang => of(lang),
      setDefaultLang: lang => {
      },
      getBrowserLang: () => 'en'
    };
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        NavbarStubComponent,
        RouterOutletStubComponent
      ],
      providers: [
        {provide: AuthService, useValue: authServiceStub},
        {provide: TranslateService, useValue: translateServiceStub}
      ]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
