import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {RestApiInterceptor} from "./rest-api.interceptor";


/** Http interceptor providers in outside-in order */
export const httpInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: RestApiInterceptor, multi: true},
];
