import {Injectable} from '@angular/core';
import {Observable} from "rxjs/internal/Observable";
import {NewFile} from "./new-file";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class FilesService {

  constructor(private http: HttpClient) {
  }

  addFileToTask(projectId: string, taskId: string, newFile: NewFile): Observable<string> {
    return this.http.post(`/api/projects/${projectId}/tasks/${taskId}/files`, newFile, {
      responseType: 'text'
    });
  }
}
