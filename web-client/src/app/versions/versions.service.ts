import {Injectable} from '@angular/core';
import {VersionInfo} from './version-info';
import {HttpClient} from '@angular/common/http';
import * as FileSaver from 'file-saver';
import {FileMetadata} from '../files/file-metadata';
import {ToastNotificationService} from '../shared/toast-notification.service';

@Injectable()
export class VersionsService {

  constructor(private http: HttpClient,
              private toastNotification: ToastNotificationService) {
  }

  downloadVersion(projectId: string, taskId: string, file: FileMetadata, version: VersionInfo) {
    this.http.get(`/api/projects/${projectId}/tasks/${taskId}/files/${file.id}/versions/${version.saveDate}/content`, {
      responseType: 'blob'
    }).subscribe((data: Blob) => {
      FileSaver.saveAs(data, `${file.name}.${file.extension}`);
    }, () => {
      this.toastNotification.error('dws.files.details.notifications.download.failure');
    });
  }
}
