import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AddFileComponent} from "./add-file/add-file.component";
import {AuthGuardService} from "../auth/auth-guard.service";
import {FileDetailsComponent} from './file-details/file-details.component';

const filesRoutes: Routes = [
  {
    path: 'projects/:projectId/tasks/:taskId/files/add',
    component: AddFileComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'projects/:projectId/tasks/:taskId/files/:fileId',
    component: FileDetailsComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'projects/:projectId/tasks/:taskId/files',
    redirectTo: 'projects/:projectId/tasks/:taskId'
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(filesRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class FilesRoutingModule {

}
