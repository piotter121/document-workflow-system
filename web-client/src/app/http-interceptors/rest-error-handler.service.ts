import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {TranslateService} from '@ngx-translate/core';
import {ToastrService} from 'ngx-toastr';
import {ErrorMessage} from '../shared/error-message';
import {catchError} from 'rxjs/operators';
import {ValidationError} from '../shared/validation-error';
import {FieldError} from '../shared/field-error';

@Injectable()
export class RestErrorHandlerService implements HttpInterceptor {

  private apiRegexp: RegExp = /^\/auth\/*./;

  constructor(private translate: TranslateService,
              private toastr: ToastrService) {
  }

  handle(errorResponse: HttpErrorResponse) {
    switch (errorResponse.status) {
      case 400:
        this.handleBadRequestResponse(errorResponse);
        break;
      case 500:
        this.handleInternalServerError(errorResponse);
        break;
      default:
        this.defaultHandle(errorResponse);
    }
  }

  private handleBadRequestResponse(errorResponse: HttpErrorResponse) {
    const validationError: ValidationError = errorResponse.error;
    validationError.fieldErrors.forEach((fieldError: FieldError) => {
      const fieldTranslationCode = `dws.httpErrors.BadRequest.field.${fieldError.field}`;
      const messageTranslationCode = `dws.httpErrors.BadRequest.message.${fieldError.message}`;
      this.translate.get([fieldTranslationCode, messageTranslationCode])
        .subscribe(translation => this.toastr.error(translation[messageTranslationCode], translation[fieldTranslationCode]));
    });
  }

  private defaultHandle(errorResponse: HttpErrorResponse) {
    const errorMessage: ErrorMessage = errorResponse.error;
    this.translate.get(`dws.httpErrors.${errorMessage.errorCode}`, errorMessage.params)
      .subscribe((translation: string) => this.toastr.error(translation));
  }

  private handleInternalServerError(errorResponse: HttpErrorResponse) {
    this.translate.get('dws.httpErrors.InternalServerError')
      .subscribe((translation: string) => {
        this.toastr.error(`${errorResponse.name}: ${errorResponse.message}`, translation);
      });
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.apiRegexp.test(req.url))
      return next.handle(req).pipe(catchError((err: HttpErrorResponse) => {
        this.handle(err);
        return throwError(err);
      }));
    else
      return next.handle(req);
  }
}
