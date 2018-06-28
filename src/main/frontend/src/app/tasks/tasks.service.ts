import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NewTask} from "./new-task";
import {Observable} from "rxjs";
import {map} from "rxjs/operators"
import {TaskInfo} from "./task-info";

@Injectable()
export class TasksService {

  constructor(private http: HttpClient) {
  }

  createTask(newTask: NewTask, projectId: string): Observable<string> {
    return this.http.post<any>(`/api/projects/${projectId}/tasks`, newTask)
      .pipe(map(response => response.taskId));
  }

  getTaskInfo(projectId: string, taskId: string): Observable<TaskInfo> {
    return this.http.get<TaskInfo>(`/api/projects/${projectId}/tasks/${taskId}`);
  }
}
