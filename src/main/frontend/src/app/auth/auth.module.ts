import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {AuthGuardService} from "./auth-guard.service";
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {JwtModule, JwtModuleOptions} from "@auth0/angular-jwt";
import {AuthRoutingModule} from "./auth-routing.module";
import {TranslateModule} from "@ngx-translate/core";
import {UserService} from "./user.service";

const jwtOptions: JwtModuleOptions = {
  config: {
    tokenGetter: () => localStorage.getItem('token'),
    headerName: 'X-AUTH-TOKEN',
    skipWhenExpired: true,
    authScheme: ''
  }
};

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    JwtModule.forRoot(jwtOptions),
    AuthRoutingModule,
    TranslateModule
  ],
  declarations: [
    LoginComponent,
    RegisterComponent
  ],
  providers: [
    AuthGuardService,
    AuthService,
    UserService
  ]
})
export class AuthModule {
}
