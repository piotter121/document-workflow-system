import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {RestApiInterceptor} from './rest-api.interceptor';
import {RestErrorHandlerService} from './rest-error-handler.service';

/** Http interceptor providers in outside-in order */
export const httpInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: RestApiInterceptor, multi: true},
  {provide: HTTP_INTERCEPTORS, useClass: RestErrorHandlerService, multi: true}
];
