import {Injectable} from '@angular/core';
import {UserInfo} from './user-info';
import {AuthService} from './auth.service';

@Injectable()
export class UserService {

  private _currentUser: UserInfo = null;
  private _currentUserChanged: boolean = false;

  constructor(private authService: AuthService) {
    this.updateCurrentUser(authService.claims);
    authService.claimsObservable
      .subscribe(claims => this.updateCurrentUser(claims));
  }

  private updateCurrentUser(claims: any) {
    this._currentUserChanged = true;
    this._currentUser = !!claims ? new UserInfo(claims.sub, claims.name) : null;
  }

  get currentUser(): UserInfo {
    return this._currentUser;
  }

  get currentUserChanged(): boolean {
    return this._currentUserChanged;
  }
}
