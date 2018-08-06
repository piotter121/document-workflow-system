import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {tap} from 'rxjs/operators';
import {NewUser} from './register/new-user';

interface AuthToken {
  token: string;
}

@Injectable()
export class AuthService {

  claimsObservable: Subject<any> = new Subject();

  constructor(public jwtHelper: JwtHelperService, private http: HttpClient) {
    this.updateClaims();
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
      this.updateClaims();
    }));
  }

  logout(): void {
    localStorage.removeItem('token');
    this.updateClaims();
  }

  register(newUser: NewUser): Observable<null> {
    return this.http.post<null>('/auth/register', newUser);
  }

  get claims(): any {
    const token = localStorage.getItem('token');
    return this.jwtHelper.decodeToken(token);
  }

  private updateClaims() {
    this.claimsObservable.next(this.claims);
  }

}
