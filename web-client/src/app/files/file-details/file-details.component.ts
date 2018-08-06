import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {map, switchMap} from 'rxjs/operators';
import {FileMetadata} from '../file-metadata';
import {FilesService} from '../files.service';
import {TaskSummary} from '../../tasks/task-summary';
import {TasksService} from '../../tasks/tasks.service';
import {UserService} from '../../auth/user.service';
import {UserInfo} from '../../auth/user-info';
import {GlobalsService} from '../../shared/globals.service';
import {combineLatest, Observable} from 'rxjs';
import {VersionInfo} from '../../versions/version-info';
import {ToastNotificationService} from '../../shared/toast-notification.service';
import {VersionsService} from '../../versions/versions.service';
import {DifferenceType} from '../../versions/difference-type.enum';

@Component({
  selector: 'file-details',
  templateUrl: './file-details.component.html',
  styleUrls: ['./file-details.component.css']
})
export class FileDetailsComponent implements OnInit {

  file: FileMetadata;
  task: TaskSummary;
  projectId: string;
  taskId: string;
  fileId: string;
  private currentUser: UserInfo;
  isCurrentUserTaskAdministrator: boolean = false;

  private _fileMetadataObservable: Observable<FileMetadata>;
  private _taskSummaryObservable: Observable<TaskSummary>;
  private _projectIdObservable: Observable<string>;

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
    this.currentUser = this.userService.currentUser;
    this.listenToIds();
    this.listenToTaskSummary();
    this.listenToTaskAdministrator();
    this.listenToFileMetadata();
    combineLatest(this._taskSummaryObservable, this._fileMetadataObservable, this._projectIdObservable)
      .subscribe(values => {
        const task: TaskSummary = values[0];
        const file: FileMetadata = values[1];
        const projectId: string = values[2];
        this.globals.title.next({
          main: task.name,
          small: file.name,
          routerLink: ['/projects', projectId, 'tasks', task.id]
        });
      });
  }

  private listenToTaskAdministrator() {
    this.route.paramMap.pipe(switchMap(paramMap => {
      const projectId = paramMap.get('projectId');
      const taskId = paramMap.get('taskId');
      return this.tasksService.getTaskAdministrator(projectId, taskId);
    })).subscribe(taskAdministrator => {
      this.isCurrentUserTaskAdministrator = this.currentUser.equals(taskAdministrator);
    });
  }

  private listenToIds() {
    this.route.paramMap.subscribe(paramMap => {
      this.projectId = paramMap.get('projectId');
      this.taskId = paramMap.get('taskId');
      this.fileId = paramMap.get('fileId');
    });
    this._projectIdObservable = this.route.paramMap.pipe(map(paramMap => paramMap.get('projectId')));
  }

  private listenToTaskSummary() {
    this._taskSummaryObservable = this.route.paramMap.pipe(switchMap(paramMap => {
      const projectId = paramMap.get('projectId');
      const taskId = paramMap.get('taskId');
      return this.tasksService.getTaskSummary(projectId, taskId);
    }));
    this._taskSummaryObservable.subscribe(taskSummary => this.task = taskSummary);
  }

  private returnToTaskDetails() {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['/projects', this.projectId, 'tasks', this.taskId]);
  }

  private listenToFileMetadata() {
    this._fileMetadataObservable = this.route.paramMap.pipe(switchMap(paramMap => {
      const taskId = paramMap.get('taskId');
      const fileId = paramMap.get('fileId');
      const projectId = paramMap.get('projectId');
      return this.filesService.getFileMetadata(projectId, taskId, fileId);
    }));
    this._fileMetadataObservable.subscribe(file => this.file = file, () => this.returnToTaskDetails());
  }

  markToConfirm() {
    this.filesService.markToConfirm(this.projectId, this.taskId, this.fileId).then(() => {
      this.file.markedToConfirm = true;
      this.toastNotification.success('dws.files.details.notifications.markToConfirm.success');
    }, () => {
      this.toastNotification.error('dws.files.details.notifications.markToConfirm.failure');
    });
  }

  confirmFile() {
    this.filesService.confirmFile(this.projectId, this.taskId, this.fileId)
      .subscribe(() => {
        this.file.confirmed = true;
        this.toastNotification.success('dws.files.details.notifications.confirm.success');
      }, () => {
        this.toastNotification.error('dws.files.details.notifications.confirm.failure');
      });
  }

  deleteFile() {
    this.filesService.deleteFile(this.projectId, this.taskId, this.fileId)
      .subscribe(() => {
        this.toastNotification.success('dws.files.details.notifications.deleteFile.success', {
          'name': this.file.name
        });
        // noinspection JSIgnoredPromiseFromCall
        this.router.navigate(['/projects', this.projectId, 'tasks', this.taskId]);
      }, () => {
        this.toastNotification.error('dws.files.details.notifications.deleteFile.failure', {
          'name': this.file.name
        });
      });
  }

  downloadVersion(version: VersionInfo) {
    this.versionsService.downloadVersion(this.projectId, this.taskId, this.file, version);
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
