import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {NewTask} from './new-task';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {TaskInfo} from './task-info';
import {TaskSummary} from './task-summary';
import {UserInfo} from '../auth/user-info';

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

  deleteTask(projectId: string, taskId: string): Observable<any> {
    return this.http.delete<any>(`/api/projects/${projectId}/tasks/${taskId}`);
  }

  getTaskSummary(projectId: string, taskId: string): Observable<TaskSummary> {
    return this.http.get<TaskSummary>(`/api/projects/${projectId}/tasks/${taskId}/summary`);
  }

  getTaskAdministrator(projectId: string, taskId: string): Observable<UserInfo> {
    return this.http.get<UserInfo>(`/api/projects/${projectId}/tasks/${taskId}/administrator`);
  }

  addParticipantToTask(projectId: string, taskId: string, email: string): Observable<UserInfo[]> {
    return this.http.put<UserInfo[]>(`/api/projects/${projectId}/tasks/${taskId}/participants`, email);
  }

  removeParticipant(participant: UserInfo, projectId: string, taskId: string): Observable<UserInfo[]> {
    return this.http.delete<UserInfo[]>(`/api/projects/${projectId}/tasks/${taskId}/participants`, {
      params: {
        'email': participant.email
      }
    });
  }
}
