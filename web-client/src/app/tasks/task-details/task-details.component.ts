import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {TaskInfo} from '../task-info';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {catchError, switchMap} from 'rxjs/operators';
import {TasksService} from '../tasks.service';
import {GlobalsService} from '../../shared/globals.service';
import {UserService} from '../../auth/user.service';
import {UserInfo} from '../../auth/user-info';
import {ToastNotificationService} from '../../shared/toast-notification.service';

@Component({
  selector: 'task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit, OnDestroy {
  task$: Observable<TaskInfo>;
  currentUser: UserInfo;

  constructor(private route: ActivatedRoute,
              private tasksService: TasksService,
              private router: Router,
              private globals: GlobalsService,
              private userService: UserService,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.globals.route = this.route;
    this.task$ = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        const projectId = params.get('projectId');
        const taskId = params.get('taskId');
        return this.tasksService.getTaskInfo(projectId, taskId);
      }),
      catchError(err => {
        this.onGetTaskError();
        return throwError(err);
      })
    );
    this.currentUser = this.userService.currentUser;
  }

  ngOnDestroy() {
    this.globals.route = null;
  }

  private onGetTaskError() {
    this.toastNotification.error('dws.task.details.getFailure');
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['../../'], {
      relativeTo: this.route
    });
  }

  get projectId(): string {
    return this.route.snapshot.paramMap.get('projectId');
  }

  get taskId(): string {
    return this.route.snapshot.paramMap.get('taskId');
  }

  deleteTask() {
    this.tasksService.deleteTask(this.projectId, this.taskId)
      .subscribe(() => {
        this.toastNotification.success('dws.task.details.admin.deleteTask.success');
        // noinspection JSIgnoredPromiseFromCall
        this.router.navigate(['../../'], {
          relativeTo: this.route
        });
      }, () => this.toastNotification.error('dws.task.details.admin.deleteTask.failure'));
  }
}
