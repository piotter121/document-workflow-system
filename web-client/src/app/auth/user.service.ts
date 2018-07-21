import {Injectable} from '@angular/core';
import {UserInfo} from "./user-info";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable()
export class UserService {

  constructor(
    private jwtHelper: JwtHelperService,
  ) {
  }

  get currentUser(): UserInfo {
    let claims = this.jwtHelper.decodeToken(localStorage.getItem('token'));
    return new UserInfo(claims.sub, claims.name);
  }
}
