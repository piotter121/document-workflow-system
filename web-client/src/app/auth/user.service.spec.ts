import {inject, TestBed} from '@angular/core/testing';

import {UserService} from './user.service';
import {AuthService} from './auth.service';
import {of} from 'rxjs';

describe('UserService', () => {
  beforeEach(() => {
    const authServiceStub: Partial<AuthService> = {
      claims$: of({})
    };
    TestBed.configureTestingModule({
      providers: [
        UserService,
        {provide: AuthService, useValue: authServiceStub}
      ]
    });
  });

  it('should be created', inject([UserService], (service: UserService) => {
    expect(service).toBeTruthy();
  }));
});
