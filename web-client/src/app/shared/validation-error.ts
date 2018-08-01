import {FieldError} from './field-error';

export interface ValidationError {
  fieldErrors: FieldError[];
}
