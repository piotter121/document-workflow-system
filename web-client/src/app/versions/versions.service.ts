import {Injectable} from '@angular/core';
import {VersionInfo} from './version-info';
import {HttpClient} from '@angular/common/http';
import * as FileSaver from 'file-saver';
import {FileMetadata} from '../files/file-metadata';
import {ToastNotificationService} from '../shared/toast-notification.service';
import {Observable} from 'rxjs';
import {DifferenceType} from './difference-type.enum';

export interface FileContent {
  lines: string[];
}

export interface Difference {
  previousSectionStart: number;
  previousSectionSize: number;
  newSectionStart: number;
  newSectionSize: number;
  differenceType: DifferenceType;
}

export interface DiffData {
  oldContent: FileContent;
  newContent: FileContent;
  differences: Difference[];
}

export interface NewVersionForm {
  projectId: string;
  taskId: string;
  fileId: string;
  file: File;
  versionString: string;
  message: string;
}

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

  getVersionInfo(projectId: string, taskId: string, fileId: string, versionId: string): Observable<VersionInfo> {
    return this.http.get<VersionInfo>(`/api/projects/${projectId}/tasks/${taskId}/files/${fileId}/versions/${versionId}`);
  }

  getDiffData(projectId: string, taskId: string, fileId: string, versionId: string): Observable<DiffData> {
    return this.http.get<DiffData>(`/api/projects/${projectId}/tasks/${taskId}/files/${fileId}/versions/${versionId}/diffData`);
  }

  addNewVersion(data: NewVersionForm): Observable<number> {
    const formData = new FormData();
    formData.append('file', data.file);
    formData.append('versionString', data.versionString);
    formData.append('message', data.message);
    return this.http.post<number>(`/api/projects/${data.projectId}/tasks/${data.taskId}/files/${data.fileId}/versions`, formData);
  }
}
