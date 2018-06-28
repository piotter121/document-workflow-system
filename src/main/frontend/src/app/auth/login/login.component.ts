import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  error: any;

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  get email(): AbstractControl {
    return this.form.get('email');
  }

  get password(): AbstractControl {
    return this.form.get('password');
  }

  login() {
    const val = this.form.value;
    if (val.email && val.password && this.form.valid) {
      this.authService.login(val.email, val.password)
        .subscribe(
          () => this.router.navigateByUrl('/projects'),
          (error: HttpErrorResponse) => this.handleError(error)
        );
    }
  }

  handleError(errorResponse: HttpErrorResponse) {
    this.error = errorResponse.error;
  }

}
