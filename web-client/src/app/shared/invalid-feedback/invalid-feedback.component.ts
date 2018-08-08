import {Component, Input} from '@angular/core';
import {AbstractControl} from '@angular/forms';

@Component({
  selector: 'invalid-feedback',
  templateUrl: './invalid-feedback.component.html',
  styleUrls: ['./invalid-feedback.component.css']
})
export class InvalidFeedbackComponent {

  @Input()
  control: AbstractControl;

  get showMessages(): boolean {
    return !this.control.pending && this.control.invalid && (this.control.touched || this.control.dirty);
  }

  constructor() { }

  get errorsKeys(): string[] {
    return Object.keys(this.control.errors);
  }

  errorValue(error: string): any {
    return this.control.errors[error];
  }
}
