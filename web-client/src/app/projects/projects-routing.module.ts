import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AllProjectsComponent} from "./all-projects/all-projects.component";
import {ProjectDetailsComponent} from "./project-details/project-details.component";
import {AuthGuardService} from "../auth/auth-guard.service";
import {AddProjectComponent} from "./add-project/add-project.component";

const projectsRoutes: Routes = [
  {
    path: 'projects/add',
    component: AddProjectComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'projects/:projectId',
    component: ProjectDetailsComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'projects',
    component: AllProjectsComponent,
    canActivate: [AuthGuardService]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(projectsRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class ProjectsRoutingModule {
}
