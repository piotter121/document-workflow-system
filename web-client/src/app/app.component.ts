import {Component, OnInit} from '@angular/core';
import {AuthService} from "./auth/auth.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private authService: AuthService,
              private translate: TranslateService) {}

  ngOnInit() {
    this.translate.setDefaultLang('en');
    this.translate.use(this.translate.getBrowserLang());
  }

  isLoggedIn(): boolean {
    return this.authService.isAuthenticated();
  }
}
