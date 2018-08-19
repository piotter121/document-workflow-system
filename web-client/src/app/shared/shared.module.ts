import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {InvalidFeedbackComponent} from './invalid-feedback/invalid-feedback.component';
import {AppValidatorsService} from './app-validators.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ToastNotificationService} from './toast-notification.service';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule
  ],
  declarations: [
    InvalidFeedbackComponent,
  ],
  providers: [
    AppValidatorsService,
    ToastNotificationService
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    NgbModule,
    InvalidFeedbackComponent
  ]
})
export class SharedModule {
}
