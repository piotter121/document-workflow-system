import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {TranslateService} from "@ngx-translate/core";

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
              private router: Router,
              private translate: TranslateService) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
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
