import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {ProjectsService} from '../projects.service';
import {catchError, map, switchMap} from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';
import {ProjectInfo} from '../project-info';
import {UserService} from '../../auth/user.service';
import {UserInfo} from '../../auth/user-info';
import {GlobalsService} from '../../shared/globals.service';
import {ToastNotificationService} from '../../shared/toast-notification.service';

@Component({
  selector: 'project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css']
})
export class ProjectDetailsComponent implements OnInit, OnDestroy {

  project$: Observable<ProjectInfo>;
  currentUser: UserInfo;
  isProjectAdmin$: Observable<boolean>;
  hasTasks$: Observable<boolean>;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private projectsService: ProjectsService,
              private userService: UserService,
              private globals: GlobalsService,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.globals.route = this.route;
    this.currentUser = this.userService.currentUser;
    this.project$ = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.projectsService.getProjectInfo(params.get('projectId'))),
      catchError(err => {
        this.toastNotification.error('dws.project.details.getFailure');
        // noinspection JSIgnoredPromiseFromCall
        this.router.navigate(['/projects']);
        return throwError(err);
      })
    );
    this.isProjectAdmin$ = this.project$.pipe(map(project => this.currentUser.equals(project.administrator)));
    this.hasTasks$ = this.project$.pipe(map(project => project.tasks.length > 0));
  }

  ngOnDestroy() {
    this.globals.route = null;
  }

  deleteProject(project: ProjectInfo) {
    this.projectsService.deleteProject(project.id)
      .subscribe(
        () => this.router.navigate(['/projects']),
        () => this.toastNotification.error('dws.project.details.toolbar.actions.delete.failure')
      );
  }

}
