import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {InvalidFeedbackComponent} from './invalid-feedback/invalid-feedback.component';
import {AppValidatorsService} from "./app-validators.service";

@NgModule({
  imports: [
    CommonModule,
    TranslateModule
  ],
  declarations: [
    InvalidFeedbackComponent
  ],
  providers: [
    AppValidatorsService
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    InvalidFeedbackComponent
  ]
})
export class SharedModule {
}
