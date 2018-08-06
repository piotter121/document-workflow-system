import {NgModule} from '@angular/core';
import {VersionsService} from './versions.service';
import {SharedModule} from '../shared/shared.module';
import {AddVersionComponent} from './add-version/add-version.component';
import {VersionDetailsComponent} from './version-details/version-details.component';
import {VersionsRoutingModule} from './versions-routing.module';

@NgModule({
  imports: [
    SharedModule,
    VersionsRoutingModule
  ],
  providers: [
    VersionsService
  ],
  declarations: [AddVersionComponent, VersionDetailsComponent]
})
export class VersionsModule {
}
