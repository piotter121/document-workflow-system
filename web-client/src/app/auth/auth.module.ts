import {NgModule} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {AuthGuardService} from "./auth-guard.service";
import {AuthService} from "./auth.service";
import {JwtModule, JwtModuleOptions} from "@auth0/angular-jwt";
import {AuthRoutingModule} from "./auth-routing.module";
import {UserService} from "./user.service";
import {SharedModule} from "../shared/shared.module";

export const jwtOptions: JwtModuleOptions = {
  config: {
    tokenGetter: () => localStorage.getItem('token'),
    headerName: 'X-AUTH-TOKEN',
    skipWhenExpired: true,
    authScheme: ''
  }
};

@NgModule({
  imports: [
    SharedModule,
    JwtModule.forRoot(jwtOptions),
    AuthRoutingModule
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
