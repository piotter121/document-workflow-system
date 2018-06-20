import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AddTaskComponent} from "./add-task/add-task.component";
import {AuthGuardService} from "../auth/auth-guard.service";
import {TaskDetailsComponent} from "./task-details/task-details.component";

const tasksRoutes: Routes = [
  {
    path: 'projects/:projectId/tasks/add',
    component: AddTaskComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'projects/:projectId/tasks/:taskId',
    component: TaskDetailsComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'projects/:projectId/tasks',
    redirectTo: 'projects/:projectId'
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(tasksRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class TasksRoutingModule {}
