import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {ProjectsService} from "../projects.service";
import {switchMap, tap} from "rxjs/operators";
import {Observable} from "rxjs";
import {ProjectInfo} from "../project-info";
import {UserService} from "../../auth/user.service";
import {UserInfo} from "../../auth/user-info";
import {HttpErrorResponse} from "@angular/common/http";
import {TaskSummary} from "../../tasks/task-summary";

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css']
})
export class ProjectDetailsComponent implements OnInit {

  isError: boolean = false;
  project$: Observable<ProjectInfo>;
  project: ProjectInfo;
  currentUser: UserInfo;
  isProjectAdmin: boolean;
  hasTasks: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private projectsService: ProjectsService,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.currentUser = this.userService.currentUser;
    this.project$ = this.route.paramMap.pipe(
      tap(() => this.isError = false),
      switchMap((params: ParamMap) => this.projectsService.getProjectInfo(params.get('projectId')))
    );
    this.project$.subscribe((project: ProjectInfo) => {
      this.project = project;
      this.isProjectAdmin = project.administrator.email === this.currentUser.email;
      this.hasTasks = project.tasks.length > 0;
    }, this.registerHttpError);
  }

  private registerHttpError(error: HttpErrorResponse) {
    this.isError = true;
    console.error(error);
  }

  deleteProject() {
    if (this.project)
      this.projectsService.deleteProject(this.project.id)
        .subscribe(() => this.router.navigate(['/projects']));
  }

  // noinspection JSMethodCanBeStatic
  trackByTasks(index: number, task: TaskSummary): string {
    return task.id;
  }
}
