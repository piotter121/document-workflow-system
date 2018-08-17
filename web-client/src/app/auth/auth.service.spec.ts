import {inject, TestBed} from '@angular/core/testing';

import {AuthService} from './auth.service';
import {JwtModule} from '@auth0/angular-jwt';
import {jwtOptions} from './auth.module';
import {HttpClient} from '@angular/common/http';

describe('AuthService', () => {
  let httpClientSpy: { get: jasmine.Spy, post: jasmine.Spy };

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post']);

    TestBed.configureTestingModule({
      imports: [
        JwtModule.forRoot(jwtOptions)
      ],
      providers: [
        AuthService,
        {provide: HttpClient, useValue: httpClientSpy}
      ]
    });
  });

  it('should be created', inject([AuthService], (service: AuthService) => {
    expect(service).toBeTruthy();
  }));
});
