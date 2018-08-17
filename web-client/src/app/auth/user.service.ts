import {Injectable} from '@angular/core';
import {UserInfo} from './user-info';
import {AuthService} from './auth.service';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable()
export class UserService {

  private _currentUser: UserInfo = null;
  private readonly _currentUser$: Observable<UserInfo>;

  constructor(private authService: AuthService) {
    this._currentUser$ = authService.claims$
      .pipe(map(claims => !!claims ? new UserInfo(claims.sub, claims.name) : null));
    this._currentUser$.subscribe(userInfo => this._currentUser = userInfo);
  }

  get currentUser(): UserInfo {
    return this._currentUser;
  }

  get currentUser$(): Observable<UserInfo> {
    return this._currentUser$;
  }
}
