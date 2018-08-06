import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuardService} from '../auth/auth-guard.service';
import {AddVersionComponent} from './add-version/add-version.component';
import {VersionDetailsComponent} from './version-details/version-details.component';

const pathBase: string = 'projects/:projectId/tasks/:taskId/files/:fileId/versions';

const versionsRoutes: Routes = [
  {
    path: pathBase + '/add',
    component: AddVersionComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: pathBase + '/:versionId',
    component: VersionDetailsComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: pathBase,
    redirectTo: 'projects/:projectId/tasks/:taskId'
  }
];

@NgModule({
  imports: [RouterModule.forChild(versionsRoutes)],
  exports: [RouterModule]
})
export class VersionsRoutingModule {
}
