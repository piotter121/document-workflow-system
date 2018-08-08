import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AppValidatorsService} from '../../shared/app-validators.service';
import {ToastNotificationService} from '../../shared/toast-notification.service';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  newUser: FormGroup;

  constructor(private authService: AuthService,
              private router: Router,
              private formBuilder: FormBuilder,
              private appValidators: AppValidatorsService,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.newUser = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email], this.appValidators.nonExistingUserEmail()],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      passwordRepeated: ['', [Validators.required, Validators.minLength(6)]]
    }, {
      validator: RegisterComponent.MatchPassword
    });
  }

  get email() {
    return this.newUser.get('email');
  }

  get firstName() {
    return this.newUser.get('firstName');
  }

  get lastName() {
    return this.newUser.get('lastName');
  }

  get passwordRepeated() {
    return this.newUser.get('passwordRepeated');
  }

  get password() {
    return this.newUser.get('password');
  }

  register() {
    this.authService.register({
      email: this.email.value,
      firstName: this.firstName.value,
      lastName: this.lastName.value,
      password: this.password.value
    }).subscribe(
      () => this.router.navigate(['/login']),
      () => this.toastNotification.error('dws.auth.register.failure')
    );
  }

  static MatchPassword(abstractControl: AbstractControl) {
    let password: AbstractControl = abstractControl.get('password');
    let passwordValue: string = password.value;
    let passwordRepeated: AbstractControl = abstractControl.get('passwordRepeated');
    let passwordRepeatedValue: string = passwordRepeated.value;
    let notMatched: boolean = passwordValue && passwordRepeatedValue
      && passwordValue != passwordRepeatedValue;
    let passwordRepeatedErrors = passwordRepeated.errors;
    if (notMatched) {
      if (passwordRepeatedErrors) {
        passwordRepeated.errors['MatchPassword'] = true;
      } else {
        passwordRepeated.setErrors({
          'MatchPassword': true
        });
      }
    } else if (passwordRepeatedErrors) {
      delete passwordRepeated.errors['MatchPassword'];
    }
  }
}
