import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TaskDetailsComponent} from './task-details/task-details.component';
import {TaskSummaryComponent} from './task-summary/task-summary.component';
import {AddTaskComponent} from './add-task/add-task.component';
import {TasksRoutingModule} from "./tasks-routing.module";
import {TranslateModule} from "@ngx-translate/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TasksService} from "./tasks.service";

@NgModule({
  imports: [
    CommonModule,
    TasksRoutingModule,
    TranslateModule,
    FormsModule,
    ReactiveFormsModule
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
