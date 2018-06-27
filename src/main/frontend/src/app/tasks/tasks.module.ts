import {NgModule} from '@angular/core';
import {TaskDetailsComponent} from './task-details/task-details.component';
import {TaskSummaryComponent} from './task-summary/task-summary.component';
import {AddTaskComponent} from './add-task/add-task.component';
import {TasksRoutingModule} from "./tasks-routing.module";
import {TasksService} from "./tasks.service";
import {SharedModule} from "../shared/shared.module";

@NgModule({
  imports: [
    SharedModule,
    TasksRoutingModule
  ],
  declarations: [
    TaskDetailsComponent,
    TaskSummaryComponent,
    AddTaskComponent
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
