import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import {HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {tap} from 'rxjs/operators';
import {NewUser} from "./register/new-user";

export class AuthToken {
  token: string;
}

@Injectable()
export class AuthService {

  constructor(public jwtHelper: JwtHelperService,
              private http: HttpClient) { }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return !this.jwtHelper.isTokenExpired(token);
  }

  login(email: string, password: string): Observable<AuthToken> {
    return this.http.get<AuthToken>('/auth/token', {
      params: {email, password}
    }).pipe(tap((response: AuthToken) => localStorage.setItem('token', response.token)));
  }

  logout() {
    localStorage.removeItem('token');
  }

  register(newUser: NewUser): Observable<any> {
    return this.http.post<any>('/auth/register', newUser);
  }

}
