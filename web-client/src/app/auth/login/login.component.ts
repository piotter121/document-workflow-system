import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {ToastNotificationService} from '../../shared/toast-notification.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private router: Router,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  get email(): AbstractControl {
    return this.loginForm.get('email');
  }

  get password(): AbstractControl {
    return this.loginForm.get('password');
  }

  login() {
    const val = this.loginForm.value;
    if (val.email && val.password && this.loginForm.valid) {
      this.authService.login(val.email, val.password)
        .subscribe(
          () => this.router.navigateByUrl('/projects'),
          () => this.toastNotification.error('dws.auth.login.failure')
        );
    }
  }

}
