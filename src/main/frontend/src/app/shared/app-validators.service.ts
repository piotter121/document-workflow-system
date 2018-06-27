import {Injectable} from "@angular/core";
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class AppValidatorsService {
  constructor(
    private http: HttpClient
  ) {
  }

  private checkIfUserExists(email: string): Observable<boolean> {
    return this.http.get<boolean>('/api/user/exists', {
      params: {
        'email': email
      }
    });
  }

  existingUserEmail(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      let value: string = control.value;
      return this.checkIfUserExists(value)
        .pipe(map((result: boolean) => result ? null : {'userNotExists': true}));
    };
  }

}
