import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProjectSummary} from "./project-summary";
import {NewProject} from "./new-project";
import {ProjectInfo} from "./project-info";

@Injectable()
export class ProjectsService {
  constructor(private http: HttpClient) {}

  getAllUserProjects(): Observable<ProjectSummary[]> {
    return this.http.get<ProjectSummary[]>('/api/projects');
  }

  createProject(project: NewProject): Observable<string> {
    return this.http.post('/api/projects', project, {
      responseType: 'text'
    });
  }

  getProjectInfo(projectId: string): Observable<ProjectInfo> {
    return this.http.get<ProjectInfo>('/api/projects/' + projectId);
  }

  deleteProject(projectId: string): Observable<any> {
    return this.http.delete<any>('/api/projects/' + projectId);
  }
}
