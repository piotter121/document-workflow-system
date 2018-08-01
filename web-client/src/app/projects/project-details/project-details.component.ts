import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {ProjectsService} from "../projects.service";
import {switchMap} from "rxjs/operators";
import {Observable} from "rxjs";
import {ProjectInfo} from "../project-info";
import {UserService} from "../../auth/user.service";
import {UserInfo} from "../../auth/user-info";
import {HttpErrorResponse} from "@angular/common/http";
import {TaskSummary} from "../../tasks/task-summary";
import {ToastrService} from "ngx-toastr";
import {TranslateService} from "@ngx-translate/core";
import {GlobalsService} from '../../shared/globals.service';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css']
})
export class ProjectDetailsComponent implements OnInit {

  project$: Observable<ProjectInfo>;
  project: ProjectInfo;
  currentUser: UserInfo;
  isProjectAdmin: boolean;
  hasTasks: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private projectsService: ProjectsService,
    private userService: UserService,
    private toastr: ToastrService,
    private translate: TranslateService,
    private globals: GlobalsService
  ) {
  }

  ngOnInit() {
    this.currentUser = this.userService.currentUser;
    this.project$ = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.projectsService.getProjectInfo(params.get('projectId')))
    );
    this.project$.subscribe((project: ProjectInfo) => {
      this.project = project;
      this.isProjectAdmin = project.administrator.email === this.currentUser.email;
      this.hasTasks = project.tasks.length > 0;
      this.translate.get('dws.project.header').subscribe(translation => {
        this.globals.title.next({
          main: translation,
          routerLink: ['/projects'],
          small: this.project.name
        });
      });
    }, this.onGetProjectError.bind(this));
  }

  private onGetProjectError(error: HttpErrorResponse) {
    this.router.navigate(['/projects'])
      .then(() => {
        let data = error.error;
        if (data && data.errorCode)
          this.translate.get(`dws.httpErrors.${data.errorCode}`, data.params)
            .subscribe((translation: string) => this.toastr.error(translation));
      });
  }

  deleteProject() {
    if (this.project)
      this.projectsService.deleteProject(this.project.id)
        .subscribe(() => this.router.navigate(['/projects']));
  }

}
