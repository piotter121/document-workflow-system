import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "src/environments/environment";

@Injectable()
export class RestApiInterceptor implements HttpInterceptor {
  private restApi: string = environment.restApi;

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req.clone({
      url: this.restApi + req.url
    }));
  }
}
