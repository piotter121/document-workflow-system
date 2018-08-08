import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';

@Injectable()
export class RestApiInterceptor implements HttpInterceptor {
  private restApi: string = environment.restApi;
  private pattern: RegExp = /^\/api\/|\/auth\//;

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.pattern.test(req.url)) {
      const request = req.clone({
        url: this.restApi + req.url
      });
      return next.handle(request);
    } else {
      return next.handle(req);
    }
  }
}
