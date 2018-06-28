import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NewUser} from "./new-user";
import {AppValidatorsService} from "../../shared/app-validators.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  newUser: FormGroup;
  errors: string[];

  constructor(private authService: AuthService,
              private router: Router,
              private formBuilder: FormBuilder,
              private appValidators: AppValidatorsService) { }

  ngOnInit() {
    this.errors = [];
    this.newUser = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email], this.appValidators.nonExistingUserEmail()],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      passwordRepeated: ['', [Validators.required, Validators.minLength(6)]]
    }, {
      validator: RegisterComponent.MatchPassword
    })
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
    this.authService.register(new NewUser(
      this.newUser.get('email').value,
      this.newUser.get('firstName').value,
      this.newUser.get('lastName').value,
      this.newUser.get('password').value
    )).subscribe(
      () => this.router.navigate(['login']),
      (error: HttpErrorResponse) => this.handleRegisterError(error)
    );
  }

  private handleRegisterError(error: HttpErrorResponse) {
    if (error.status == 400) {
      let validationError: any = error.error;
      this.errors = validationError.fieldErrors.map((fieldError: any) => fieldError.message);
    } else {
      this.errors = [error.message];
    }
  }

  static MatchPassword(abstractControl: AbstractControl) {
    let password: AbstractControl = abstractControl.get('password');
    let passwordValue: string = password.value;
    let passwordRepeated: AbstractControl = abstractControl.get('passwordRepeated');
    let passwordRepeatedValue: string = passwordRepeated.value;
    let notMatched: boolean = passwordValue && passwordRepeatedValue
    && passwordValue != passwordRepeatedValue;
    let passwordRepeatedErrors = abstractControl.get('passwordRepeated').errors;
    if (notMatched) {
      if (passwordRepeatedErrors) {
        abstractControl.get('passwordRepeated').errors['MatchPassword'] = true;
      } else {
        abstractControl.get('passwordRepeated').setErrors({
          'MatchPassword': true
        });
      }
    } else if (passwordRepeatedErrors) {
      delete abstractControl.get('passwordRepeated').errors['MatchPassword'];
    }
  }
}
