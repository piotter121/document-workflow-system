import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {InvalidFeedbackComponent} from './invalid-feedback/invalid-feedback.component';
import {AppValidatorsService} from "./app-validators.service";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { FormatDatesPipe } from './format-dates.pipe';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule
  ],
  declarations: [
    InvalidFeedbackComponent,
    FormatDatesPipe
  ],
  providers: [
    AppValidatorsService
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    NgbModule,
    InvalidFeedbackComponent,
    FormatDatesPipe
  ]
})
export class SharedModule {
}
