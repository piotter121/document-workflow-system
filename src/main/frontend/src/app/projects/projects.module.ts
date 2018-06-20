import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AllProjectsComponent} from './all-projects/all-projects.component';
import {ProjectDetailsComponent} from './project-details/project-details.component';
import {HttpClientModule} from "@angular/common/http";
import {AddProjectComponent} from './add-project/add-project.component';
import {ProjectSummaryComponent} from './project-summary/project-summary.component';
import {ProjectsRoutingModule} from "./projects-routing.module";
import {ProjectsService} from "./projects.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {TasksModule} from "../tasks/tasks.module";

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    ProjectsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    TasksModule
  ],
  declarations: [
    AllProjectsComponent,
    ProjectDetailsComponent,
    AddProjectComponent,
    ProjectSummaryComponent
  ],
  providers: [
    ProjectsService
  ]
})
export class ProjectsModule {
}
