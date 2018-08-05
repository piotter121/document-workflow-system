import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ToastrService} from 'ngx-toastr';

@Injectable()
export class ToastNotificationService {

  constructor(private translate: TranslateService,
              private toastr: ToastrService) { }

  success(messageCode: string, params?: Object) {
    this.translate.get(messageCode, params)
      .subscribe(translation => this.toastr.success(translation));
  }

  error(messageCode: string, params?: Object) {
    this.translate.get(messageCode, params)
      .subscribe(translation => this.toastr.error(translation));
  }
}
