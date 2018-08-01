import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';
import {UserInfo} from '../auth/user-info';
import {UserService} from '../auth/user.service';
import {GlobalsService} from '../shared/globals.service';
import {NavbarData} from './navbar-data';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {

  loggedIn: UserInfo;
  isCollapsed: boolean = false;
  private titleSubscription: Subscription;
  title: NavbarData;

  constructor(private authService: AuthService,
              private router: Router,
              private userService: UserService,
              private globals: GlobalsService) {
  }

  ngOnInit() {
    this.loggedIn = this.userService.currentUser;
    this.titleSubscription = this.globals.title.subscribe(title => this.title = title);
  }

  ngOnDestroy() {
    this.titleSubscription.unsubscribe();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

}
