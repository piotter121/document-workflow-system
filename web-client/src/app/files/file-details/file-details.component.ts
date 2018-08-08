import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {catchError, map, switchMap} from 'rxjs/operators';
import {FileMetadata} from '../file-metadata';
import {FilesService} from '../files.service';
import {TasksService} from '../../tasks/tasks.service';
import {UserService} from '../../auth/user.service';
import {UserInfo} from '../../auth/user-info';
import {GlobalsService} from '../../shared/globals.service';
import {Observable, throwError} from 'rxjs';
import {VersionInfo} from '../../versions/version-info';
import {ToastNotificationService} from '../../shared/toast-notification.service';
import {VersionsService} from '../../versions/versions.service';
import {DifferenceType} from '../../versions/difference-type.enum';

@Component({
  selector: 'file-details',
  templateUrl: './file-details.component.html',
  styleUrls: ['./file-details.component.css']
})
export class FileDetailsComponent implements OnInit, OnDestroy {

  file$: Observable<FileMetadata>;
  private currentUser: UserInfo;
  isCurrentUserTaskAdministrator$: Observable<boolean>;


  constructor(private route: ActivatedRoute,
              private router: Router,
              private filesService: FilesService,
              private tasksService: TasksService,
              private userService: UserService,
              private globals: GlobalsService,
              private toastNotification: ToastNotificationService,
              private versionsService: VersionsService) {
  }

  ngOnInit() {
    this.globals.route = this.route;
    this.currentUser = this.userService.currentUser;
    this.listenToTaskAdministrator();
    this.listenToFileMetadata();
  }

  ngOnDestroy(): void {
    this.globals.route = null;
  }

  private listenToTaskAdministrator() {
    this.isCurrentUserTaskAdministrator$ = this.route.paramMap.pipe(switchMap(paramMap => {
      const projectId = paramMap.get('projectId');
      const taskId = paramMap.get('taskId');
      return this.tasksService.getTaskAdministrator(projectId, taskId);
    }), map((taskAdministrator: UserInfo) => this.currentUser.equals(taskAdministrator)));
  }

  private returnToTaskDetails() {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['../../'], {
      relativeTo: this.route
    });
  }

  private listenToFileMetadata() {
    this.file$ = this.route.paramMap.pipe(
      switchMap(paramMap => {
        const taskId = paramMap.get('taskId');
        const fileId = paramMap.get('fileId');
        const projectId = paramMap.get('projectId');
        return this.filesService.getFileMetadata(projectId, taskId, fileId);
      }),
      catchError((err) => {
        this.returnToTaskDetails();
        return throwError(err);
      })
    );
  }

  get projectId(): string {
    return this.route.snapshot.paramMap.get('projectId');
  }

  get taskId(): string {
    return this.route.snapshot.paramMap.get('taskId');
  }

  get fileId(): string {
    return this.route.snapshot.paramMap.get('fileId');
  }

  markToConfirm() {
    this.filesService.markToConfirm(this.projectId, this.taskId, this.fileId).then(() => {
      this.listenToFileMetadata();
      this.toastNotification.success('dws.files.details.notifications.markToConfirm.success');
    }, () => this.toastNotification.error('dws.files.details.notifications.markToConfirm.failure'));
  }

  confirmFile() {
    this.filesService.confirmFile(this.projectId, this.taskId, this.fileId)
      .subscribe(() => {
        this.listenToFileMetadata();
        this.toastNotification.success('dws.files.details.notifications.confirm.success');
      }, () => {
        this.toastNotification.error('dws.files.details.notifications.confirm.failure');
      });
  }

  deleteFile() {
    this.filesService.deleteFile(this.projectId, this.taskId, this.fileId)
      .subscribe(() => {
        this.toastNotification.success('dws.files.details.notifications.deleteFile.success');
        // noinspection JSIgnoredPromiseFromCall
        this.router.navigate(['../../'], {
          relativeTo: this.route
        });
      }, () => this.toastNotification.error('dws.files.details.notifications.deleteFile.failure'));
  }

  downloadVersion(version: VersionInfo, file: FileMetadata) {
    this.versionsService.downloadVersion(this.projectId, this.taskId, file, version);
  }

  // noinspection JSMethodCanBeStatic
  numberOfDifferences(version: VersionInfo): number {
    return version.differences.length || 0;
  }

  numberOfModifiedLines(version: VersionInfo): number {
    return version.differences
      .filter(difference => difference.differenceType === DifferenceType.Modification)
      .map(difference => difference.newSectionSize)
      .reduce((a, b) => a + b, 0);
  }

  numberOfInsertedLines(version: VersionInfo): number {
    return version.differences
      .filter(difference => difference.differenceType === DifferenceType.Insert)
      .map(difference => difference.newSectionSize)
      .reduce((a, b) => a + b, 0);
  }

  numberOfDeletedLines(version: VersionInfo): number {
    return version.differences
      .filter(difference => difference.differenceType === DifferenceType.Delete)
      .map(difference => difference.previousSectionSize)
      .reduce((a, b) => a + b, 0);
  }
}
