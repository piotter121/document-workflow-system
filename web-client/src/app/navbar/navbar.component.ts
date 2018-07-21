import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {UserInfo} from "../auth/user-info";
import {UserService} from "../auth/user.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  loggedIn: UserInfo;
  isCollapsed: boolean;

  constructor(private authService: AuthService,
              private router: Router,
              private userService: UserService) { }

  ngOnInit() {
    this.loggedIn = this.userService.currentUser;
    this.isCollapsed = false;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

}
