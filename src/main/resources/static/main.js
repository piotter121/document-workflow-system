(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error('Cannot find module "' + req + '".');
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/app-routing.module.ts":
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var appRoutes = [
    {
        path: '',
        redirectTo: 'projects',
        pathMatch: 'full'
    },
    {
        path: '**',
        redirectTo: 'projects'
    }
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            imports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"].forRoot(appRoutes)
            ],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<app-navbar *ngIf=\"isLoggedIn()\"></app-navbar>\n<div class=\"container-fluid mt-2\">\n  <router-outlet></router-outlet>\n</div>\n"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _auth_auth_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./auth/auth.service */ "./src/app/auth/auth.service.ts");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AppComponent = /** @class */ (function () {
    function AppComponent(authService, translate) {
        this.authService = authService;
        this.translate = translate;
    }
    AppComponent.prototype.ngOnInit = function () {
        this.translate.setDefaultLang('en');
        this.translate.use(navigator.languages[1] || navigator.language);
    };
    AppComponent.prototype.isLoggedIn = function () {
        return this.authService.isAuthenticated();
    };
    AppComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")]
        }),
        __metadata("design:paramtypes", [_auth_auth_service__WEBPACK_IMPORTED_MODULE_1__["AuthService"],
            _ngx_translate_core__WEBPACK_IMPORTED_MODULE_2__["TranslateService"]])
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: HttpLoaderFactory, AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HttpLoaderFactory", function() { return HttpLoaderFactory; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _auth_auth_module__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./auth/auth.module */ "./src/app/auth/auth.module.ts");
/* harmony import */ var _projects_projects_module__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./projects/projects.module */ "./src/app/projects/projects.module.ts");
/* harmony import */ var _tasks_tasks_module__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./tasks/tasks.module */ "./src/app/tasks/tasks.module.ts");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./app-routing.module */ "./src/app/app-routing.module.ts");
/* harmony import */ var _navbar_navbar_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./navbar/navbar.component */ "./src/app/navbar/navbar.component.ts");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _ngx_translate_http_loader__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @ngx-translate/http-loader */ "./node_modules/@ngx-translate/http-loader/esm5/ngx-translate-http-loader.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_common_locales_pl__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/common/locales/pl */ "./node_modules/@angular/common/locales/pl.js");
/* harmony import */ var _angular_common_locales_pl__WEBPACK_IMPORTED_MODULE_13___default = /*#__PURE__*/__webpack_require__.n(_angular_common_locales_pl__WEBPACK_IMPORTED_MODULE_13__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};














Object(_angular_common__WEBPACK_IMPORTED_MODULE_12__["registerLocaleData"])(_angular_common_locales_pl__WEBPACK_IMPORTED_MODULE_13___default.a, 'pl');
function HttpLoaderFactory(http) {
    return new _ngx_translate_http_loader__WEBPACK_IMPORTED_MODULE_11__["TranslateHttpLoader"](http);
}
var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_3__["AppComponent"],
                _navbar_navbar_component__WEBPACK_IMPORTED_MODULE_8__["NavbarComponent"]
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
                _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_2__["NgbModule"].forRoot(),
                _ngx_translate_core__WEBPACK_IMPORTED_MODULE_9__["TranslateModule"].forRoot({
                    loader: {
                        provide: _ngx_translate_core__WEBPACK_IMPORTED_MODULE_9__["TranslateLoader"],
                        useFactory: HttpLoaderFactory,
                        deps: [_angular_common_http__WEBPACK_IMPORTED_MODULE_10__["HttpClient"]]
                    }
                }),
                _auth_auth_module__WEBPACK_IMPORTED_MODULE_4__["AuthModule"],
                _tasks_tasks_module__WEBPACK_IMPORTED_MODULE_6__["TasksModule"],
                _projects_projects_module__WEBPACK_IMPORTED_MODULE_5__["ProjectsModule"],
                _app_routing_module__WEBPACK_IMPORTED_MODULE_7__["AppRoutingModule"]
            ],
            providers: [
                {
                    provide: _angular_core__WEBPACK_IMPORTED_MODULE_1__["LOCALE_ID"],
                    useValue: 'pl'
                }
            ],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_3__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/auth/auth-guard.service.ts":
/*!********************************************!*\
  !*** ./src/app/auth/auth-guard.service.ts ***!
  \********************************************/
/*! exports provided: AuthGuardService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthGuardService", function() { return AuthGuardService; });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _auth_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./auth.service */ "./src/app/auth/auth.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AuthGuardService = /** @class */ (function () {
    function AuthGuardService(auth, router) {
        this.auth = auth;
        this.router = router;
    }
    AuthGuardService.prototype.canActivate = function () {
        if (!this.auth.isAuthenticated()) {
            this.router.navigate(['login']);
            return false;
        }
        return true;
    };
    AuthGuardService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        __metadata("design:paramtypes", [_auth_service__WEBPACK_IMPORTED_MODULE_2__["AuthService"], _angular_router__WEBPACK_IMPORTED_MODULE_0__["Router"]])
    ], AuthGuardService);
    return AuthGuardService;
}());



/***/ }),

/***/ "./src/app/auth/auth-routing.module.ts":
/*!*********************************************!*\
  !*** ./src/app/auth/auth-routing.module.ts ***!
  \*********************************************/
/*! exports provided: AuthRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthRoutingModule", function() { return AuthRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _register_register_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./register/register.component */ "./src/app/auth/register/register.component.ts");
/* harmony import */ var _login_login_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./login/login.component */ "./src/app/auth/login/login.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var authRoutes = [
    {
        path: 'login',
        component: _login_login_component__WEBPACK_IMPORTED_MODULE_3__["LoginComponent"]
    },
    {
        path: 'register',
        component: _register_register_component__WEBPACK_IMPORTED_MODULE_2__["RegisterComponent"]
    }
];
var AuthRoutingModule = /** @class */ (function () {
    function AuthRoutingModule() {
    }
    AuthRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(authRoutes)
            ],
            exports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]
            ]
        })
    ], AuthRoutingModule);
    return AuthRoutingModule;
}());



/***/ }),

/***/ "./src/app/auth/auth.module.ts":
/*!*************************************!*\
  !*** ./src/app/auth/auth.module.ts ***!
  \*************************************/
/*! exports provided: AuthModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthModule", function() { return AuthModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _login_login_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./login/login.component */ "./src/app/auth/login/login.component.ts");
/* harmony import */ var _register_register_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./register/register.component */ "./src/app/auth/register/register.component.ts");
/* harmony import */ var _auth_guard_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./auth-guard.service */ "./src/app/auth/auth-guard.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _auth_service__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./auth.service */ "./src/app/auth/auth.service.ts");
/* harmony import */ var _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @auth0/angular-jwt */ "./node_modules/@auth0/angular-jwt/index.js");
/* harmony import */ var _auth_routing_module__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./auth-routing.module */ "./src/app/auth/auth-routing.module.ts");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");
/* harmony import */ var _user_service__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./user.service */ "./src/app/auth/user.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};













var jwtOptions = {
    config: {
        tokenGetter: function () { return localStorage.getItem('token'); },
        headerName: 'X-AUTH-TOKEN',
        skipWhenExpired: true,
        authScheme: ''
    }
};
var AuthModule = /** @class */ (function () {
    function AuthModule() {
    }
    AuthModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _angular_router__WEBPACK_IMPORTED_MODULE_5__["RouterModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_6__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ReactiveFormsModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_7__["HttpClientModule"],
                _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_9__["JwtModule"].forRoot(jwtOptions),
                _auth_routing_module__WEBPACK_IMPORTED_MODULE_10__["AuthRoutingModule"],
                _ngx_translate_core__WEBPACK_IMPORTED_MODULE_11__["TranslateModule"]
            ],
            declarations: [
                _login_login_component__WEBPACK_IMPORTED_MODULE_2__["LoginComponent"],
                _register_register_component__WEBPACK_IMPORTED_MODULE_3__["RegisterComponent"]
            ],
            providers: [
                _auth_guard_service__WEBPACK_IMPORTED_MODULE_4__["AuthGuardService"],
                _auth_service__WEBPACK_IMPORTED_MODULE_8__["AuthService"],
                _user_service__WEBPACK_IMPORTED_MODULE_12__["UserService"]
            ]
        })
    ], AuthModule);
    return AuthModule;
}());



/***/ }),

/***/ "./src/app/auth/auth.service.ts":
/*!**************************************!*\
  !*** ./src/app/auth/auth.service.ts ***!
  \**************************************/
/*! exports provided: AuthToken, AuthService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthToken", function() { return AuthToken; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthService", function() { return AuthService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @auth0/angular-jwt */ "./node_modules/@auth0/angular-jwt/index.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var AuthToken = /** @class */ (function () {
    function AuthToken() {
    }
    return AuthToken;
}());

var AuthService = /** @class */ (function () {
    function AuthService(jwtHelper, http) {
        this.jwtHelper = jwtHelper;
        this.http = http;
    }
    AuthService.prototype.isAuthenticated = function () {
        var token = localStorage.getItem('token');
        return !this.jwtHelper.isTokenExpired(token);
    };
    AuthService.prototype.login = function (email, password) {
        return this.http.get('/auth/token', {
            params: { email: email, password: password }
        }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["tap"])(function (response) { return localStorage.setItem('token', response.token); }));
    };
    AuthService.prototype.logout = function () {
        localStorage.removeItem('token');
    };
    AuthService.prototype.register = function (newUser) {
        return this.http.post('/auth/register', newUser);
    };
    AuthService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])(),
        __metadata("design:paramtypes", [_auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_1__["JwtHelperService"],
            _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]])
    ], AuthService);
    return AuthService;
}());



/***/ }),

/***/ "./src/app/auth/login/login.component.css":
/*!************************************************!*\
  !*** ./src/app/auth/login/login.component.css ***!
  \************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".form-control.ng-touched.ng-invalid:not(form) {\n  border-color: rgb(220, 53, 69);\n}\n"

/***/ }),

/***/ "./src/app/auth/login/login.component.html":
/*!*************************************************!*\
  !*** ./src/app/auth/login/login.component.html ***!
  \*************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\">\n  <div class=\"row\">\n    <div class=\"offset-md-4 col-md-4\">\n      <div class=\"card\">\n        <div class=\"card-header\">\n          <h3 class=\"card-title\">Zaloguj się</h3>\n        </div>\n        <div class=\"card-body\">\n          <div class=\"alert alert-danger\" [hidden]=\"!error\">\n            {{'dws.httpErrors.' + error?.errorCode | translate:error?.params}}\n          </div>\n          <form #loginForm=\"ngForm\" role=\"form\" [formGroup]=\"form\" (ngSubmit)=\"login()\">\n            <fieldset>\n              <div class=\"form-group\">\n                <input class=\"form-control\" placeholder=\"Adres e-mail\"\n                       type=\"text\" formControlName=\"email\">\n              </div>\n              <div class=\"form-group\">\n                <input class=\"form-control\" placeholder=\"Hasło\" type=\"password\"\n                       formControlName=\"password\">\n              </div>\n              <button class=\"btn btn-lg btn-primary btn-block\" [disabled]=\"!loginForm.form.valid\" type=\"submit\">\n                <i class=\"fa fa-sign-in\" aria-hidden=\"true\"></i>Zaloguj się\n              </button>\n            </fieldset>\n          </form>\n          <a routerLink=\"/register\" class=\"btn btn-info btn-block\">\n            <i class=\"fa fa-check\" aria-hidden=\"true\"></i> Rejestracja\n          </a>\n        </div>\n      </div>\n    </div>\n  </div>\n</div>\n"

/***/ }),

/***/ "./src/app/auth/login/login.component.ts":
/*!***********************************************!*\
  !*** ./src/app/auth/login/login.component.ts ***!
  \***********************************************/
/*! exports provided: LoginComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginComponent", function() { return LoginComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _auth_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../auth.service */ "./src/app/auth/auth.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var LoginComponent = /** @class */ (function () {
    function LoginComponent(fb, authService, router, translate) {
        this.fb = fb;
        this.authService = authService;
        this.router = router;
        this.translate = translate;
    }
    LoginComponent.prototype.ngOnInit = function () {
        this.form = this.fb.group({
            email: ['', [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].email]],
            password: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required]
        });
    };
    LoginComponent.prototype.login = function () {
        var _this = this;
        var val = this.form.value;
        if (val.email && val.password && this.form.valid) {
            this.authService.login(val.email, val.password)
                .subscribe(function () { return _this.router.navigateByUrl('/projects'); }, function (error) { return _this.handleError(error); });
        }
    };
    LoginComponent.prototype.handleError = function (errorResponse) {
        this.error = errorResponse.error;
    };
    LoginComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-login',
            template: __webpack_require__(/*! ./login.component.html */ "./src/app/auth/login/login.component.html"),
            styles: [__webpack_require__(/*! ./login.component.css */ "./src/app/auth/login/login.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"],
            _auth_service__WEBPACK_IMPORTED_MODULE_2__["AuthService"],
            _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"],
            _ngx_translate_core__WEBPACK_IMPORTED_MODULE_4__["TranslateService"]])
    ], LoginComponent);
    return LoginComponent;
}());



/***/ }),

/***/ "./src/app/auth/register/new-user.ts":
/*!*******************************************!*\
  !*** ./src/app/auth/register/new-user.ts ***!
  \*******************************************/
/*! exports provided: NewUser */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NewUser", function() { return NewUser; });
var NewUser = /** @class */ (function () {
    function NewUser(email, firstName, lastName, password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
    return NewUser;
}());



/***/ }),

/***/ "./src/app/auth/register/register.component.css":
/*!******************************************************!*\
  !*** ./src/app/auth/register/register.component.css ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".error-span {\n  display: block;\n}\n"

/***/ }),

/***/ "./src/app/auth/register/register.component.html":
/*!*******************************************************!*\
  !*** ./src/app/auth/register/register.component.html ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<form [formGroup]=\"newUser\" (ngSubmit)=\"register()\" novalidate>\n  <legend>\n    Poniżej znajduje się formularz rejestracji w systemie. Wszystkie pola są wymagane.\n  </legend>\n  <fieldset>\n    <div class=\"form-group row\">\n      <label class=\"col-md-2 col-form-label\" for=\"email\">\n        Adres e-mail\n      </label>\n      <div class=\"col-md-5\">\n        <input id=\"email\" type=\"email\" class=\"form-control\" formControlName=\"email\"\n               [ngClass]=\"{'is-invalid': isInvalid(email)}\">\n      </div>\n    </div>\n    <div class=\"form-group row\">\n      <label class=\"col-md-2 col-form-label\" for=\"firstName\">\n        Imię\n      </label>\n      <div class=\"col-md-5\">\n        <input id=\"firstName\" type=\"text\" class=\"form-control\" formControlName=\"firstName\"\n               [ngClass]=\"{'is-invalid': isInvalid(firstName)}\">\n      </div>\n    </div>\n    <div class=\"form-group row\">\n      <label class=\"col-md-2 col-form-label\" for=\"lastName\">\n        Nazwisko\n      </label>\n      <div class=\"col-md-5\">\n        <input id=\"lastName\" type=\"text\" class=\"form-control\" formControlName=\"lastName\"\n               [ngClass]=\"{'is-invalid': isInvalid(lastName)}\">\n      </div>\n    </div>\n    <div class=\"form-group row\">\n      <label class=\"col-md-2 col-form-label\" for=\"password\">\n        Hasło\n      </label>\n      <div class=\"col-md-5\">\n        <input id=\"password\" type=\"password\" class=\"form-control\" formControlName=\"password\"\n               [ngClass]=\"{'is-invalid': isInvalid(password)}\">\n        <div class=\"invalid-feedback\">\n          <span class=\"error-span\" *ngIf=\"password.errors?.minlength\">Hasło powinno mieć co najmniej 6 znaków</span>\n        </div>\n      </div>\n    </div>\n    <div class=\"form-group row\">\n      <label class=\"col-md-2 col-form-label\" for=\"passwordRepeated\">\n        Powtórz hasło\n      </label>\n      <div class=\"col-md-5\">\n        <input id=\"passwordRepeated\" type=\"password\" class=\"form-control\"\n               [ngClass]=\"{'is-invalid': isInvalid(passwordRepeated)}\"\n               formControlName=\"passwordRepeated\">\n        <div class=\"invalid-feedback\">\n          <span class=\"error-span\" *ngIf=\"passwordRepeated.errors?.MatchPassword\">Podane hasło jest inne!</span>\n          <span class=\"error-span\" *ngIf=\"passwordRepeated.errors?.minlength\">Hasło powinno mieć co najmniej 6 znaków</span>\n        </div>\n      </div>\n    </div>\n    <div class=\"alert alert-danger\" [hidden]=\"errors?.length == 0\">\n      <p *ngFor=\"let error of errors\">\n        {{error}}\n      </p>\n    </div>\n    <button type=\"submit\" class=\"btn btn-secondary\" [disabled]=\"newUser.invalid\">\n      <i class=\"fa fa-check\" aria-hidden=\"true\"></i> Zarejestruj się\n    </button>\n  </fieldset>\n</form>\n"

/***/ }),

/***/ "./src/app/auth/register/register.component.ts":
/*!*****************************************************!*\
  !*** ./src/app/auth/register/register.component.ts ***!
  \*****************************************************/
/*! exports provided: RegisterComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RegisterComponent", function() { return RegisterComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _auth_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../auth.service */ "./src/app/auth/auth.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _new_user__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./new-user */ "./src/app/auth/register/new-user.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var RegisterComponent = /** @class */ (function () {
    function RegisterComponent(authService, router, formBuilder) {
        this.authService = authService;
        this.router = router;
        this.formBuilder = formBuilder;
    }
    RegisterComponent_1 = RegisterComponent;
    RegisterComponent.prototype.ngOnInit = function () {
        this.errors = [];
        this.newUser = this.formBuilder.group({
            email: ['', [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].email]],
            firstName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required],
            lastName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required],
            password: ['', [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].minLength(6)]],
            passwordRepeated: ['', [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].minLength(6)]]
        }, {
            validator: RegisterComponent_1.MatchPassword
        });
    };
    Object.defineProperty(RegisterComponent.prototype, "email", {
        get: function () {
            return this.newUser.get('email');
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RegisterComponent.prototype, "firstName", {
        get: function () {
            return this.newUser.get('firstName');
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RegisterComponent.prototype, "lastName", {
        get: function () {
            return this.newUser.get('lastName');
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RegisterComponent.prototype, "passwordRepeated", {
        get: function () {
            return this.newUser.get('passwordRepeated');
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(RegisterComponent.prototype, "password", {
        get: function () {
            return this.newUser.get('password');
        },
        enumerable: true,
        configurable: true
    });
    RegisterComponent.prototype.register = function () {
        var _this = this;
        this.authService.register(new _new_user__WEBPACK_IMPORTED_MODULE_4__["NewUser"](this.newUser.get('email').value, this.newUser.get('firstName').value, this.newUser.get('lastName').value, this.newUser.get('password').value)).subscribe(function () { return _this.router.navigate(['login']); }, function (error) { return _this.handleRegisterError(error); });
    };
    RegisterComponent.prototype.handleRegisterError = function (error) {
        if (error.status == 400) {
            var validationError = error.error;
            this.errors = validationError.fieldErrors.map(function (fieldError) { return fieldError.message; });
        }
        else {
            this.errors = [error.message];
        }
    };
    RegisterComponent.MatchPassword = function (abstractControl) {
        var password = abstractControl.get('password');
        var passwordValue = password.value;
        var passwordRepeated = abstractControl.get('passwordRepeated');
        var passwordRepeatedValue = passwordRepeated.value;
        var notMatched = passwordValue && passwordRepeatedValue
            && passwordValue != passwordRepeatedValue;
        var passwordRepeatedErrors = abstractControl.get('passwordRepeated').errors;
        if (notMatched) {
            if (passwordRepeatedErrors) {
                abstractControl.get('passwordRepeated').errors['MatchPassword'] = true;
            }
            else {
                abstractControl.get('passwordRepeated').setErrors({
                    'MatchPassword': true
                });
            }
        }
        else if (passwordRepeatedErrors) {
            delete abstractControl.get('passwordRepeated').errors['MatchPassword'];
        }
    };
    RegisterComponent.prototype.isInvalid = function (control) {
        return control.invalid && (control.touched || control.dirty);
    };
    RegisterComponent = RegisterComponent_1 = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-register',
            template: __webpack_require__(/*! ./register.component.html */ "./src/app/auth/register/register.component.html"),
            styles: [__webpack_require__(/*! ./register.component.css */ "./src/app/auth/register/register.component.css")]
        }),
        __metadata("design:paramtypes", [_auth_service__WEBPACK_IMPORTED_MODULE_1__["AuthService"],
            _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"]])
    ], RegisterComponent);
    return RegisterComponent;
    var RegisterComponent_1;
}());



/***/ }),

/***/ "./src/app/auth/user-info.ts":
/*!***********************************!*\
  !*** ./src/app/auth/user-info.ts ***!
  \***********************************/
/*! exports provided: UserInfo */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserInfo", function() { return UserInfo; });
var UserInfo = /** @class */ (function () {
    function UserInfo(email, fullName) {
        this.email = email;
        this.fullName = fullName;
    }
    return UserInfo;
}());



/***/ }),

/***/ "./src/app/auth/user.service.ts":
/*!**************************************!*\
  !*** ./src/app/auth/user.service.ts ***!
  \**************************************/
/*! exports provided: UserService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserService", function() { return UserService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _user_info__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./user-info */ "./src/app/auth/user-info.ts");
/* harmony import */ var _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @auth0/angular-jwt */ "./node_modules/@auth0/angular-jwt/index.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var UserService = /** @class */ (function () {
    function UserService(jwtHelper) {
        this.jwtHelper = jwtHelper;
    }
    Object.defineProperty(UserService.prototype, "currentUser", {
        get: function () {
            var claims = this.jwtHelper.decodeToken(localStorage.getItem('token'));
            return new _user_info__WEBPACK_IMPORTED_MODULE_1__["UserInfo"](claims.sub, claims.name);
        },
        enumerable: true,
        configurable: true
    });
    UserService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])(),
        __metadata("design:paramtypes", [_auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_2__["JwtHelperService"]])
    ], UserService);
    return UserService;
}());



/***/ }),

/***/ "./src/app/navbar/navbar.component.css":
/*!*********************************************!*\
  !*** ./src/app/navbar/navbar.component.css ***!
  \*********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/navbar/navbar.component.html":
/*!**********************************************!*\
  !*** ./src/app/navbar/navbar.component.html ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nav class=\"navbar navbar-expand-md navbar-dark bg-dark\">\n  <ul class=\"navbar-nav\">\n    <li class=\"nav-item\">\n      <a routerLink=\"\" routerLinkActive=\"active\" class=\"nav-link\">\n        <i class=\"fa fa-home\" aria-hidden=\"true\"></i> Strona główna\n      </a>\n    </li>\n    <li class=\"nav-item\">\n      <a routerLink=\"/projects\" routerLinkActive=\"active\" class=\"nav-link\">\n        <i class=\"fa fa-folder\" aria-hidden=\"true\"></i> Projekty\n      </a>\n    </li>\n  </ul>\n\n  <p class=\"navbar-text\" *ngIf=\"loggedIn\">Zalogowany jako {{loggedIn.username}}</p>\n\n  <button type=\"button\" class=\"btn btn-secondary float-right\" (click)=\"logout()\">\n    <i class=\"fa fa-sign-out\" aria-hidden=\"true\"></i> Wyloguj\n  </button>\n</nav>\n"

/***/ }),

/***/ "./src/app/navbar/navbar.component.ts":
/*!********************************************!*\
  !*** ./src/app/navbar/navbar.component.ts ***!
  \********************************************/
/*! exports provided: NavbarComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NavbarComponent", function() { return NavbarComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _auth_auth_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../auth/auth.service */ "./src/app/auth/auth.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var NavbarComponent = /** @class */ (function () {
    function NavbarComponent(authService, router) {
        this.authService = authService;
        this.router = router;
    }
    NavbarComponent.prototype.ngOnInit = function () {
    };
    NavbarComponent.prototype.logout = function () {
        this.authService.logout();
        this.router.navigate(['login']);
    };
    NavbarComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-navbar',
            template: __webpack_require__(/*! ./navbar.component.html */ "./src/app/navbar/navbar.component.html"),
            styles: [__webpack_require__(/*! ./navbar.component.css */ "./src/app/navbar/navbar.component.css")]
        }),
        __metadata("design:paramtypes", [_auth_auth_service__WEBPACK_IMPORTED_MODULE_1__["AuthService"],
            _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"]])
    ], NavbarComponent);
    return NavbarComponent;
}());



/***/ }),

/***/ "./src/app/projects/add-project/add-project.component.css":
/*!****************************************************************!*\
  !*** ./src/app/projects/add-project/add-project.component.css ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/projects/add-project/add-project.component.html":
/*!*****************************************************************!*\
  !*** ./src/app/projects/add-project/add-project.component.html ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<legend>\n  Aby dodać nowy projekt należy wypełnić poniższy formularz.\n</legend>\n<form [formGroup]=\"newProject\" (ngSubmit)=\"createProject()\" novalidate>\n  <fieldset>\n    <div class=\"form-group row\">\n      <label class=\"col-md-2 col-form-label\" for=\"name\">\n        Nazwa projektu\n      </label>\n      <div class=\"col-md-5\">\n        <input id=\"name\" type=\"text\" class=\"form-control\" formControlName=\"name\"\n                  [ngClass]=\"{'is-invalid': isInvalid(name)}\" required>\n      </div>\n    </div>\n\n    <div class=\"form-group row\">\n      <label class=\"col-md-2 col-form-label\" for=\"description\">\n        Dodatkowy opis projektu\n      </label>\n      <div class=\"col-md-5\">\n        <textarea id=\"description\" class=\"form-control\" formControlName=\"description\"\n                  [ngClass]=\"{'is-invalid': isInvalid(description)}\"></textarea>\n      </div>\n    </div>\n\n    <div class=\"form-group row\">\n      <div class=\"col-md-offset-2 col-md-10\">\n        <button type=\"submit\" class=\"btn btn-secondary\">\n          Stwórz\n        </button>\n      </div>\n    </div>\n  </fieldset>\n</form>\n"

/***/ }),

/***/ "./src/app/projects/add-project/add-project.component.ts":
/*!***************************************************************!*\
  !*** ./src/app/projects/add-project/add-project.component.ts ***!
  \***************************************************************/
/*! exports provided: AddProjectComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AddProjectComponent", function() { return AddProjectComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _projects_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../projects.service */ "./src/app/projects/projects.service.ts");
/* harmony import */ var _new_project__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../new-project */ "./src/app/projects/new-project.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var AddProjectComponent = /** @class */ (function () {
    function AddProjectComponent(formBuilder, projectsService, router) {
        this.formBuilder = formBuilder;
        this.projectsService = projectsService;
        this.router = router;
    }
    AddProjectComponent.prototype.ngOnInit = function () {
        this.newProject = this.formBuilder.group({
            name: ['', [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].maxLength(40)]],
            description: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].maxLength(1024)]
        });
    };
    AddProjectComponent.prototype.isInvalid = function (control) {
        return control.invalid && (control.touched || control.dirty);
    };
    Object.defineProperty(AddProjectComponent.prototype, "name", {
        get: function () {
            return this.newProject.get('name');
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AddProjectComponent.prototype, "description", {
        get: function () {
            return this.newProject.get('description');
        },
        enumerable: true,
        configurable: true
    });
    AddProjectComponent.prototype.createProject = function () {
        var _this = this;
        this.projectsService.createProject(new _new_project__WEBPACK_IMPORTED_MODULE_3__["NewProject"](this.name.value, this.description.value)).subscribe(function (projectId) { return _this.router.navigate(['/projects', projectId]); });
    };
    AddProjectComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-add-project',
            template: __webpack_require__(/*! ./add-project.component.html */ "./src/app/projects/add-project/add-project.component.html"),
            styles: [__webpack_require__(/*! ./add-project.component.css */ "./src/app/projects/add-project/add-project.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"],
            _projects_service__WEBPACK_IMPORTED_MODULE_2__["ProjectsService"],
            _angular_router__WEBPACK_IMPORTED_MODULE_4__["Router"]])
    ], AddProjectComponent);
    return AddProjectComponent;
}());



/***/ }),

/***/ "./src/app/projects/all-projects/all-projects.component.css":
/*!******************************************************************!*\
  !*** ./src/app/projects/all-projects/all-projects.component.css ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/projects/all-projects/all-projects.component.html":
/*!*******************************************************************!*\
  !*** ./src/app/projects/all-projects/all-projects.component.html ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"row\">\n  <div class=\"col-md-12\">\n    <div class=\"btn-toolbar\" role=\"toolbar\">\n      <div class=\"btn-group\" role=\"group\">\n        <a class=\"btn btn-primary\" routerLink=\"/projects/add\">\n          <i class=\"fa fa-plus\" aria-hidden=\"true\"></i> {{'dws.project.all.createNew' | translate}}\n        </a>\n      </div>\n    </div>\n  </div>\n</div>\n\n<div class=\"row\">\n  <div *ngIf=\"projects?.length > 0; then showProjects else showMsgBlock\"></div>\n\n  <ng-template #showProjects>\n    <div class=\"col-md-12\">\n      <div class=\"text-center\">\n        <h3 translate>dws.project.all.header</h3>\n      </div>\n      <div class=\"row\">\n        <div *ngFor=\"let project of projects; trackBy: trackByProjects\" class=\"col-md-4\">\n          <project-summary  [project]=\"project\"></project-summary>\n        </div>\n      </div>\n    </div>\n  </ng-template>\n\n  <ng-template #showMsgBlock>\n    <div class=\"col-md-8 offset-md-2\">\n      <div class=\"alert alert-info text-center\"\n           [innerHTML]=\"'dws.project.all.noProjectsMsg' | translate\"></div>\n    </div>\n  </ng-template>\n</div>\n"

/***/ }),

/***/ "./src/app/projects/all-projects/all-projects.component.ts":
/*!*****************************************************************!*\
  !*** ./src/app/projects/all-projects/all-projects.component.ts ***!
  \*****************************************************************/
/*! exports provided: AllProjectsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AllProjectsComponent", function() { return AllProjectsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _projects_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../projects.service */ "./src/app/projects/projects.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var AllProjectsComponent = /** @class */ (function () {
    function AllProjectsComponent(projectsService) {
        this.projectsService = projectsService;
    }
    AllProjectsComponent.prototype.ngOnInit = function () {
        this.loadProjects();
    };
    AllProjectsComponent.prototype.loadProjects = function () {
        var _this = this;
        this.projectsService.getAllUserProjects()
            .subscribe(function (projects) {
            _this.projects = projects;
        }, function (error) {
            console.error(error);
        });
    };
    AllProjectsComponent.prototype.trackByProjects = function (index, project) {
        return project.id;
    };
    AllProjectsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-all-projects',
            template: __webpack_require__(/*! ./all-projects.component.html */ "./src/app/projects/all-projects/all-projects.component.html"),
            styles: [__webpack_require__(/*! ./all-projects.component.css */ "./src/app/projects/all-projects/all-projects.component.css")]
        }),
        __metadata("design:paramtypes", [_projects_service__WEBPACK_IMPORTED_MODULE_1__["ProjectsService"]])
    ], AllProjectsComponent);
    return AllProjectsComponent;
}());



/***/ }),

/***/ "./src/app/projects/new-project.ts":
/*!*****************************************!*\
  !*** ./src/app/projects/new-project.ts ***!
  \*****************************************/
/*! exports provided: NewProject */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NewProject", function() { return NewProject; });
var NewProject = /** @class */ (function () {
    function NewProject(name, description) {
        this.name = name;
        this.description = description;
    }
    return NewProject;
}());



/***/ }),

/***/ "./src/app/projects/project-details/project-details.component.css":
/*!************************************************************************!*\
  !*** ./src/app/projects/project-details/project-details.component.css ***!
  \************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/projects/project-details/project-details.component.html":
/*!*************************************************************************!*\
  !*** ./src/app/projects/project-details/project-details.component.html ***!
  \*************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div *ngIf=\"project\" class=\"row\">\n  <div class=\"col-md-8\">\n    <div class=\"btn-toolbar mb-2\" role=\"toolbar\">\n      <div class=\"btn-group\" role=\"group\">\n        <div *ngIf=\"isProjectAdmin; then projectAdminGroup else projectParticipantGroup\"></div>\n        <ng-template #projectAdminGroup>\n          <a [routerLink]=\"addTaskLink\" class=\"btn btn-success\">\n            <span class=\"fa fa-plus\" aria-hidden=\"true\"></span>\n            {{'dws.project.details.toolbar.addTask' | translate}}\n          </a>\n          <button class=\"btn btn-danger\" type=\"button\" (click)=\"removeProject()\">\n            <span class=\"fa fa-trash\" aria-hidden=\"true\"></span>\n            {{'dws.project.details.toolbar.deleteProject' | translate}}\n          </button>\n        </ng-template>\n        <ng-template #projectParticipantGroup>\n          <a href=\"mailto:{{project.administrator.email}}\" class=\"btn btn-info\">\n            <span class=\"fa fa-envelope\" aria-hidden=\"true\"></span>\n            {{'dws.project.details.toolbar.mailToAdmin' | translate}}\n          </a>\n        </ng-template>\n      </div>\n    </div>\n\n    <div *ngIf=\"hasTasks; then tasksSummaries else noTasksInfo\"></div>\n    <ng-template #noTasksInfo>\n      <div class=\"alert alert-info text-center\" role=\"alert\"\n           [innerHTML]=\"'dws.project.details.noTasksAlert' | translate\"></div>\n    </ng-template>\n    <ng-template #tasksSummaries>\n      <div class=\"text-center\">\n        <h3 translate [translateParams]=\"project\">dws.project.details.taskList.header</h3>\n      </div>\n      <div class=\"row\">\n        <div *ngFor=\"let task of project.tasks; trackBy: trackByTasks\" class=\"col-md-6\">\n          <task-summary [task]=\"task\"></task-summary>\n        </div>\n      </div>\n    </ng-template>\n  </div>\n\n  <div class=\"col-md-4\">\n    <div class=\"card\">\n      <div class=\"card-header\" translate>dws.project.details.card.header</div>\n      <div class=\"list-group list-group-flush\">\n        <div class=\"list-group-item flex-column align-items-start\">\n          <small translate>dws.project.details.card.name</small><br/>\n          <span>{{project.name}}</span>\n        </div>\n        <div class=\"list-group-item flex-column align-items-start\">\n          <small translate>dws.project.details.card.description</small><br/>\n          <span>{{project.description}}</span>\n        </div>\n        <div class=\"list-group-item flex-column align-items-start\">\n          <small translate>dws.project.details.card.admin</small><br/>\n          <span>{{project.administrator.fullName}}</span>\n        </div>\n        <div class=\"list-group-item flex-column align-items-start\">\n          <small translate>dws.project.details.card.creationDate</small><br/>\n          <span>{{project.creationDate | date:'dd.MM.yyyy HH:mm'}}</span>\n        </div>\n        <div class=\"list-group-item flex-column align-items-start\">\n          <small translate>dws.project.details.card.lastModified</small><br/>\n          <span>{{project.lastModified | date:'dd.MM.yyyy HH:mm'}}</span>\n        </div>\n      </div>\n    </div>\n  </div>\n</div>\n\n<div *ngIf=\"isError\">\n  TODO template błędu\n</div>\n"

/***/ }),

/***/ "./src/app/projects/project-details/project-details.component.ts":
/*!***********************************************************************!*\
  !*** ./src/app/projects/project-details/project-details.component.ts ***!
  \***********************************************************************/
/*! exports provided: ProjectDetailsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProjectDetailsComponent", function() { return ProjectDetailsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _projects_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../projects.service */ "./src/app/projects/projects.service.ts");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _auth_user_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../auth/user.service */ "./src/app/auth/user.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var ProjectDetailsComponent = /** @class */ (function () {
    function ProjectDetailsComponent(route, router, projectsService, userService) {
        this.route = route;
        this.router = router;
        this.projectsService = projectsService;
        this.userService = userService;
        this.isError = false;
    }
    ProjectDetailsComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.currentUser = this.userService.currentUser;
        this.project$ = this.route.paramMap.pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["tap"])(function () { return _this.isError = false; }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["switchMap"])(function (params) { return _this.projectsService.getProjectInfo(params.get('projectId')); }));
        this.project$.subscribe(function (project) {
            _this.project = project;
            _this.isProjectAdmin = project.administrator.email === _this.currentUser.email;
            _this.addTaskLink = ['/projects', project.id, 'tasks', 'add'];
            _this.hasTasks = project.tasks.length > 0;
        }, this.registerHttpError);
    };
    ProjectDetailsComponent.prototype.registerHttpError = function (error) {
        this.isError = true;
        console.error(error);
    };
    ProjectDetailsComponent.prototype.removeProject = function () {
    };
    ProjectDetailsComponent.prototype.trackByTasks = function (index, task) {
        return task.id;
    };
    ProjectDetailsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-project-details',
            template: __webpack_require__(/*! ./project-details.component.html */ "./src/app/projects/project-details/project-details.component.html"),
            styles: [__webpack_require__(/*! ./project-details.component.css */ "./src/app/projects/project-details/project-details.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_1__["ActivatedRoute"],
            _angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"],
            _projects_service__WEBPACK_IMPORTED_MODULE_2__["ProjectsService"],
            _auth_user_service__WEBPACK_IMPORTED_MODULE_4__["UserService"]])
    ], ProjectDetailsComponent);
    return ProjectDetailsComponent;
}());



/***/ }),

/***/ "./src/app/projects/project-summary.ts":
/*!*********************************************!*\
  !*** ./src/app/projects/project-summary.ts ***!
  \*********************************************/
/*! exports provided: ProjectSummary */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProjectSummary", function() { return ProjectSummary; });
var ProjectSummary = /** @class */ (function () {
    function ProjectSummary() {
    }
    return ProjectSummary;
}());



/***/ }),

/***/ "./src/app/projects/project-summary/project-summary.component.css":
/*!************************************************************************!*\
  !*** ./src/app/projects/project-summary/project-summary.component.css ***!
  \************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/projects/project-summary/project-summary.component.html":
/*!*************************************************************************!*\
  !*** ./src/app/projects/project-summary/project-summary.component.html ***!
  \*************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"card bg-light\">\n  <h3 class=\"card-header\">\n    <a [routerLink]=\"[project.id]\">{{project.name}}</a>\n  </h3>\n\n  <div class=\"card-body\">\n    <p class=\"card-text\">Utworzono {{project.creationDate | date:'long'}}</p>\n    <p class=\"card-text\" *ngIf=\"project.lastModifiedFile\">\n      {{'dws.project.summary.lastModifiedFile' | translate:project.lastModifiedFile}}\n    </p>\n  </div>\n\n  <div class=\"card-footer\">\n    <span class=\"fa fa-user\"></span> {{project.numberOfParticipants}}\n    <span class=\"fa fa-tasks\"></span> {{project.numberOfTasks}}\n    <span class=\"fa fa-file\"></span> {{project.numberOfFiles}}\n  </div>\n</div>\n"

/***/ }),

/***/ "./src/app/projects/project-summary/project-summary.component.ts":
/*!***********************************************************************!*\
  !*** ./src/app/projects/project-summary/project-summary.component.ts ***!
  \***********************************************************************/
/*! exports provided: ProjectSummaryComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProjectSummaryComponent", function() { return ProjectSummaryComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _project_summary__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../project-summary */ "./src/app/projects/project-summary.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ProjectSummaryComponent = /** @class */ (function () {
    function ProjectSummaryComponent() {
    }
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])(),
        __metadata("design:type", _project_summary__WEBPACK_IMPORTED_MODULE_1__["ProjectSummary"])
    ], ProjectSummaryComponent.prototype, "project", void 0);
    ProjectSummaryComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'project-summary',
            template: __webpack_require__(/*! ./project-summary.component.html */ "./src/app/projects/project-summary/project-summary.component.html"),
            styles: [__webpack_require__(/*! ./project-summary.component.css */ "./src/app/projects/project-summary/project-summary.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], ProjectSummaryComponent);
    return ProjectSummaryComponent;
}());



/***/ }),

/***/ "./src/app/projects/projects-routing.module.ts":
/*!*****************************************************!*\
  !*** ./src/app/projects/projects-routing.module.ts ***!
  \*****************************************************/
/*! exports provided: ProjectsRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProjectsRoutingModule", function() { return ProjectsRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _all_projects_all_projects_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./all-projects/all-projects.component */ "./src/app/projects/all-projects/all-projects.component.ts");
/* harmony import */ var _project_details_project_details_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./project-details/project-details.component */ "./src/app/projects/project-details/project-details.component.ts");
/* harmony import */ var _auth_auth_guard_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../auth/auth-guard.service */ "./src/app/auth/auth-guard.service.ts");
/* harmony import */ var _add_project_add_project_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./add-project/add-project.component */ "./src/app/projects/add-project/add-project.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};






var projectsRoutes = [
    {
        path: 'projects/add',
        component: _add_project_add_project_component__WEBPACK_IMPORTED_MODULE_5__["AddProjectComponent"],
        canActivate: [_auth_auth_guard_service__WEBPACK_IMPORTED_MODULE_4__["AuthGuardService"]]
    },
    {
        path: 'projects/:projectId',
        component: _project_details_project_details_component__WEBPACK_IMPORTED_MODULE_3__["ProjectDetailsComponent"],
        canActivate: [_auth_auth_guard_service__WEBPACK_IMPORTED_MODULE_4__["AuthGuardService"]]
    },
    {
        path: 'projects',
        component: _all_projects_all_projects_component__WEBPACK_IMPORTED_MODULE_2__["AllProjectsComponent"],
        canActivate: [_auth_auth_guard_service__WEBPACK_IMPORTED_MODULE_4__["AuthGuardService"]]
    }
];
var ProjectsRoutingModule = /** @class */ (function () {
    function ProjectsRoutingModule() {
    }
    ProjectsRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(projectsRoutes)
            ],
            exports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]
            ]
        })
    ], ProjectsRoutingModule);
    return ProjectsRoutingModule;
}());



/***/ }),

/***/ "./src/app/projects/projects.module.ts":
/*!*********************************************!*\
  !*** ./src/app/projects/projects.module.ts ***!
  \*********************************************/
/*! exports provided: ProjectsModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProjectsModule", function() { return ProjectsModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _all_projects_all_projects_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./all-projects/all-projects.component */ "./src/app/projects/all-projects/all-projects.component.ts");
/* harmony import */ var _project_details_project_details_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./project-details/project-details.component */ "./src/app/projects/project-details/project-details.component.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _add_project_add_project_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./add-project/add-project.component */ "./src/app/projects/add-project/add-project.component.ts");
/* harmony import */ var _project_summary_project_summary_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./project-summary/project-summary.component */ "./src/app/projects/project-summary/project-summary.component.ts");
/* harmony import */ var _projects_routing_module__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./projects-routing.module */ "./src/app/projects/projects-routing.module.ts");
/* harmony import */ var _projects_service__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./projects.service */ "./src/app/projects/projects.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");
/* harmony import */ var _tasks_tasks_module__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ../tasks/tasks.module */ "./src/app/tasks/tasks.module.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};












var ProjectsModule = /** @class */ (function () {
    function ProjectsModule() {
    }
    ProjectsModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpClientModule"],
                _projects_routing_module__WEBPACK_IMPORTED_MODULE_7__["ProjectsRoutingModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_9__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_9__["ReactiveFormsModule"],
                _ngx_translate_core__WEBPACK_IMPORTED_MODULE_10__["TranslateModule"],
                _tasks_tasks_module__WEBPACK_IMPORTED_MODULE_11__["TasksModule"]
            ],
            declarations: [
                _all_projects_all_projects_component__WEBPACK_IMPORTED_MODULE_2__["AllProjectsComponent"],
                _project_details_project_details_component__WEBPACK_IMPORTED_MODULE_3__["ProjectDetailsComponent"],
                _add_project_add_project_component__WEBPACK_IMPORTED_MODULE_5__["AddProjectComponent"],
                _project_summary_project_summary_component__WEBPACK_IMPORTED_MODULE_6__["ProjectSummaryComponent"]
            ],
            providers: [
                _projects_service__WEBPACK_IMPORTED_MODULE_8__["ProjectsService"]
            ]
        })
    ], ProjectsModule);
    return ProjectsModule;
}());



/***/ }),

/***/ "./src/app/projects/projects.service.ts":
/*!**********************************************!*\
  !*** ./src/app/projects/projects.service.ts ***!
  \**********************************************/
/*! exports provided: ProjectsService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProjectsService", function() { return ProjectsService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ProjectsService = /** @class */ (function () {
    function ProjectsService(http) {
        this.http = http;
    }
    ProjectsService.prototype.getAllUserProjects = function () {
        return this.http.get('/api/projects');
    };
    ProjectsService.prototype.createProject = function (project) {
        return this.http.post('/api/projects', project, {
            responseType: 'text'
        });
    };
    ProjectsService.prototype.getProjectInfo = function (projectId) {
        return this.http.get('/api/projects/' + projectId);
    };
    ProjectsService.prototype.deleteProject = function (projectId) {
        return this.http.delete('/api/projects/' + projectId);
    };
    ProjectsService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])(),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"]])
    ], ProjectsService);
    return ProjectsService;
}());



/***/ }),

/***/ "./src/app/tasks/add-task/add-task.component.css":
/*!*******************************************************!*\
  !*** ./src/app/tasks/add-task/add-task.component.css ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/tasks/add-task/add-task.component.html":
/*!********************************************************!*\
  !*** ./src/app/tasks/add-task/add-task.component.html ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  add-task works!\n</p>\n"

/***/ }),

/***/ "./src/app/tasks/add-task/add-task.component.ts":
/*!******************************************************!*\
  !*** ./src/app/tasks/add-task/add-task.component.ts ***!
  \******************************************************/
/*! exports provided: AddTaskComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AddTaskComponent", function() { return AddTaskComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var AddTaskComponent = /** @class */ (function () {
    function AddTaskComponent() {
    }
    AddTaskComponent.prototype.ngOnInit = function () {
    };
    AddTaskComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-add-task',
            template: __webpack_require__(/*! ./add-task.component.html */ "./src/app/tasks/add-task/add-task.component.html"),
            styles: [__webpack_require__(/*! ./add-task.component.css */ "./src/app/tasks/add-task/add-task.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], AddTaskComponent);
    return AddTaskComponent;
}());



/***/ }),

/***/ "./src/app/tasks/task-details/task-details.component.css":
/*!***************************************************************!*\
  !*** ./src/app/tasks/task-details/task-details.component.css ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/tasks/task-details/task-details.component.html":
/*!****************************************************************!*\
  !*** ./src/app/tasks/task-details/task-details.component.html ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  task-details works!\n</p>\n"

/***/ }),

/***/ "./src/app/tasks/task-details/task-details.component.ts":
/*!**************************************************************!*\
  !*** ./src/app/tasks/task-details/task-details.component.ts ***!
  \**************************************************************/
/*! exports provided: TaskDetailsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TaskDetailsComponent", function() { return TaskDetailsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var TaskDetailsComponent = /** @class */ (function () {
    function TaskDetailsComponent() {
    }
    TaskDetailsComponent.prototype.ngOnInit = function () {
    };
    TaskDetailsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-task-details',
            template: __webpack_require__(/*! ./task-details.component.html */ "./src/app/tasks/task-details/task-details.component.html"),
            styles: [__webpack_require__(/*! ./task-details.component.css */ "./src/app/tasks/task-details/task-details.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], TaskDetailsComponent);
    return TaskDetailsComponent;
}());



/***/ }),

/***/ "./src/app/tasks/task-summary.ts":
/*!***************************************!*\
  !*** ./src/app/tasks/task-summary.ts ***!
  \***************************************/
/*! exports provided: TaskSummary */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TaskSummary", function() { return TaskSummary; });
var TaskSummary = /** @class */ (function () {
    function TaskSummary() {
    }
    return TaskSummary;
}());



/***/ }),

/***/ "./src/app/tasks/task-summary/task-summary.component.css":
/*!***************************************************************!*\
  !*** ./src/app/tasks/task-summary/task-summary.component.css ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/tasks/task-summary/task-summary.component.html":
/*!****************************************************************!*\
  !*** ./src/app/tasks/task-summary/task-summary.component.html ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"card bg-light\">\n  <h3 class=\"card-header\">\n    <a [routerLink]=\"['tasks', task.id]\">{{task.name}}</a>\n  </h3>\n\n  <div class=\"card-body\">\n    <p class=\"card-text\">Utworzono {{task.creationDate | date:'long'}}</p>\n    <p class=\"card-text\" *ngIf=\"task.lastModifiedFile\">\n      {{'dws.task.summary.lastModifiedFile' | translate:task.lastModifiedFile}}\n    </p>\n  </div>\n\n  <div class=\"card-footer\">\n    <i class=\"fa fa-file\" aria-hidden=\"true\"></i> {{task.numberOfFiles}}\n    <i class=\"fa fa-user\" aria-hidden=\"true\"></i> {{task.numberOfParticipants}}\n  </div>\n</div>\n"

/***/ }),

/***/ "./src/app/tasks/task-summary/task-summary.component.ts":
/*!**************************************************************!*\
  !*** ./src/app/tasks/task-summary/task-summary.component.ts ***!
  \**************************************************************/
/*! exports provided: TaskSummaryComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TaskSummaryComponent", function() { return TaskSummaryComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _task_summary__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../task-summary */ "./src/app/tasks/task-summary.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var TaskSummaryComponent = /** @class */ (function () {
    function TaskSummaryComponent() {
    }
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])(),
        __metadata("design:type", _task_summary__WEBPACK_IMPORTED_MODULE_1__["TaskSummary"])
    ], TaskSummaryComponent.prototype, "task", void 0);
    TaskSummaryComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'task-summary',
            template: __webpack_require__(/*! ./task-summary.component.html */ "./src/app/tasks/task-summary/task-summary.component.html"),
            styles: [__webpack_require__(/*! ./task-summary.component.css */ "./src/app/tasks/task-summary/task-summary.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], TaskSummaryComponent);
    return TaskSummaryComponent;
}());



/***/ }),

/***/ "./src/app/tasks/tasks-routing.module.ts":
/*!***********************************************!*\
  !*** ./src/app/tasks/tasks-routing.module.ts ***!
  \***********************************************/
/*! exports provided: TasksRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TasksRoutingModule", function() { return TasksRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _add_task_add_task_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./add-task/add-task.component */ "./src/app/tasks/add-task/add-task.component.ts");
/* harmony import */ var _auth_auth_guard_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../auth/auth-guard.service */ "./src/app/auth/auth-guard.service.ts");
/* harmony import */ var _task_details_task_details_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./task-details/task-details.component */ "./src/app/tasks/task-details/task-details.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};





var tasksRoutes = [
    {
        path: 'projects/:projectId/tasks/add',
        component: _add_task_add_task_component__WEBPACK_IMPORTED_MODULE_2__["AddTaskComponent"],
        canActivate: [_auth_auth_guard_service__WEBPACK_IMPORTED_MODULE_3__["AuthGuardService"]]
    },
    {
        path: 'projects/:projectId/tasks/:taskId',
        component: _task_details_task_details_component__WEBPACK_IMPORTED_MODULE_4__["TaskDetailsComponent"],
        canActivate: [_auth_auth_guard_service__WEBPACK_IMPORTED_MODULE_3__["AuthGuardService"]]
    },
    {
        path: 'projects/:projectId/tasks',
        redirectTo: 'projects/:projectId'
    }
];
var TasksRoutingModule = /** @class */ (function () {
    function TasksRoutingModule() {
    }
    TasksRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(tasksRoutes)
            ],
            exports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]
            ]
        })
    ], TasksRoutingModule);
    return TasksRoutingModule;
}());



/***/ }),

/***/ "./src/app/tasks/tasks.module.ts":
/*!***************************************!*\
  !*** ./src/app/tasks/tasks.module.ts ***!
  \***************************************/
/*! exports provided: TasksModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TasksModule", function() { return TasksModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _task_details_task_details_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./task-details/task-details.component */ "./src/app/tasks/task-details/task-details.component.ts");
/* harmony import */ var _task_summary_task_summary_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./task-summary/task-summary.component */ "./src/app/tasks/task-summary/task-summary.component.ts");
/* harmony import */ var _add_task_add_task_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./add-task/add-task.component */ "./src/app/tasks/add-task/add-task.component.ts");
/* harmony import */ var _tasks_routing_module__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./tasks-routing.module */ "./src/app/tasks/tasks-routing.module.ts");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm5/ngx-translate-core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};







var TasksModule = /** @class */ (function () {
    function TasksModule() {
    }
    TasksModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _tasks_routing_module__WEBPACK_IMPORTED_MODULE_5__["TasksRoutingModule"],
                _ngx_translate_core__WEBPACK_IMPORTED_MODULE_6__["TranslateModule"]
            ],
            declarations: [
                _task_details_task_details_component__WEBPACK_IMPORTED_MODULE_2__["TaskDetailsComponent"],
                _task_summary_task_summary_component__WEBPACK_IMPORTED_MODULE_3__["TaskSummaryComponent"],
                _add_task_add_task_component__WEBPACK_IMPORTED_MODULE_4__["AddTaskComponent"]
            ],
            exports: [
                _task_summary_task_summary_component__WEBPACK_IMPORTED_MODULE_3__["TaskSummaryComponent"]
            ]
        })
    ], TasksModule);
    return TasksModule;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /home/piotr/IdeaProjects/Document-workflow-system/src/main/frontend/src/main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map