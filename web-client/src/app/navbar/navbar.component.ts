import {Component, DoCheck, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';
import {UserInfo} from '../auth/user-info';
import {UserService} from '../auth/user.service';
import {GlobalsService} from '../shared/globals.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, DoCheck {

  loggedIn: UserInfo;
  isCollapsed: boolean = false;

  constructor(private authService: AuthService,
              private router: Router,
              private userService: UserService,
              public globals: GlobalsService) {
  }

  ngOnInit() {
    this.loggedIn = this.userService.currentUser;
  }

  ngDoCheck() {
    if (this.userService.currentUserChanged)
      this.loggedIn = this.userService.currentUser;
  }

  logout() {
    this.authService.logout();
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['/login']);
  }

  goBack() {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['../..'], {
      relativeTo: this.globals.route
    });
  }

}
