import {Injectable} from '@angular/core';
import {Observable} from "rxjs/internal/Observable";
import {HttpClient} from "@angular/common/http";
import {NewFile} from "./new-file";

@Injectable()
export class FilesService {

  constructor(private http: HttpClient) {
  }

  addFileToTask(projectId: string, taskId: string, formData: FormData): Observable<string> {
    return this.http.post(`/api/projects/${projectId}/tasks/${taskId}/files`, formData, {
      responseType: 'text'
    });
  }
}
