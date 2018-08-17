import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {map, tap} from 'rxjs/operators';
import {NewUser} from './register/new-user';

interface AuthToken {
  token: string;
}

@Injectable()
export class AuthService {

  private _token$: Subject<string> = new BehaviorSubject(localStorage.getItem('token'));
  claims$: Observable<any> = this._token$.pipe(map(token => this.jwtHelper.decodeToken(token)));
  isAuthenticated$: Observable<boolean> = this._token$.pipe(map(token => !this.jwtHelper.isTokenExpired(token)));

  constructor(private jwtHelper: JwtHelperService, private http: HttpClient) {
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return !this.jwtHelper.isTokenExpired(token);
  }

  login(email: string, password: string): Observable<AuthToken> {
    return this.http.get<AuthToken>('/auth/token', {
      params: {email, password}
    }).pipe(tap((response: AuthToken) => {
      localStorage.setItem('token', response.token);
      this._token$.next(response.token);
    }));
  }

  logout(): void {
    localStorage.removeItem('token');
    this._token$.next(null);
  }

  register(newUser: NewUser): Promise<null> {
    return this.http.post<null>('/auth/register', newUser).toPromise();
  }
}
