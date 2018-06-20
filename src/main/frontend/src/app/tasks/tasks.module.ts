import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TaskDetailsComponent} from './task-details/task-details.component';
import {TaskSummaryComponent} from './task-summary/task-summary.component';
import {AddTaskComponent} from './add-task/add-task.component';
import {TasksRoutingModule} from "./tasks-routing.module";
import {TranslateModule} from "@ngx-translate/core";

@NgModule({
  imports: [
    CommonModule,
    TasksRoutingModule,
    TranslateModule
  ],
  declarations: [
    TaskDetailsComponent,
    TaskSummaryComponent,
    AddTaskComponent
  ],
  exports: [
    TaskSummaryComponent
  ]
})
export class TasksModule {
}
