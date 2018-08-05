import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {FileMetadata} from './file-metadata';

@Injectable()
export class FilesService {

  constructor(private http: HttpClient) {
  }

  addFileToTask(projectId: string, taskId: string, formData: FormData): Observable<string> {
    return this.http.post(`/api/projects/${projectId}/tasks/${taskId}/files`, formData, {
      responseType: 'text'
    });
  }

  getFileMetadata(projectId: string, taskId: string, fileId: string): Observable<FileMetadata> {
    return this.http.get<FileMetadata>(`/api/projects/${projectId}/tasks/${taskId}/files/${fileId}`);
  }

  markToConfirm(projectId: string, taskId: string, fileId: string): Promise<null> {
    return this.http.post<null>(`/api/projects/${projectId}/tasks/${taskId}/files/${fileId}/markToConfirm`, null)
      .toPromise();
  }

  confirmFile(projectId: string, taskId: string, fileId: string): Observable<null> {
    return this.http.post<null>(`/api/projects/${projectId}/tasks/${taskId}/files/${fileId}/confirm`, null);
  }

  deleteFile(projectId: string, taskId: string, fileId: string): Observable<null> {
    return this.http.delete<null>(`/api/projects/${projectId}/tasks/${taskId}/files/${fileId}`);
  }
}
