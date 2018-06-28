import {NgModule} from '@angular/core';
import {TaskDetailsComponent} from './task-details/task-details.component';
import {TaskSummaryComponent} from './task-summary/task-summary.component';
import {AddTaskComponent} from './add-task/add-task.component';
import {TasksRoutingModule} from "./tasks-routing.module";
import {TasksService} from "./tasks.service";
import {SharedModule} from "../shared/shared.module";
import { FilesListComponent } from './files-list/files-list.component';
import { ParticipantsListComponent } from './participants-list/participants-list.component';

@NgModule({
  imports: [
    SharedModule,
    TasksRoutingModule
  ],
  declarations: [
    TaskDetailsComponent,
    TaskSummaryComponent,
    AddTaskComponent,
    FilesListComponent,
    ParticipantsListComponent
  ],
  providers: [
    TasksService
  ],
  exports: [
    TaskSummaryComponent
  ]
})
export class TasksModule {
}
