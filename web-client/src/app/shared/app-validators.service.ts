import {Injectable} from '@angular/core';
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from '@angular/forms';
import {Observable, of} from 'rxjs';
import {catchError, debounceTime, distinctUntilChanged, map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class AppValidatorsService {
  constructor(private http: HttpClient) {
  }

  nonExistingTaskNameInProject(projectId: string): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      const taskName: string = control.value;
      return this.http.get<boolean>(`/api/projects/${projectId}/tasks/exists`, {
        params: {
          'taskName': taskName
        }
      }).pipe(
        debounceTime(500),
        distinctUntilChanged(),
        catchError(() => of(false)),
        map((exists: boolean) => exists ? {'taskExists': true} : null)
      );
    };
  }

  private checkIfUserExists(email: string): Observable<boolean> {
    return this.http.get<boolean>('/api/user/exists', {
      params: {
        'email': email
      }
    }).pipe(debounceTime(500), distinctUntilChanged());
  }

  existingUserEmail(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      let value: string = control.value;
      return this.checkIfUserExists(value)
        .pipe(
          catchError(() => of(true)),
          map((exists: boolean) => exists ? null : {'userNotExists': true})
        );
    };
  }

  nonExistingUserEmail(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      let value: string = control.value;
      return this.checkIfUserExists(value)
        .pipe(
          catchError(() => of(false)),
          map((exists: boolean) => exists ? {'userExists': true} : null)
        );
    };
  }
}
