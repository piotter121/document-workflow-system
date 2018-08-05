import {NgModule} from '@angular/core';
import {VersionsService} from './versions.service';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule
  ],
  providers: [
    VersionsService
  ],
  declarations: []
})
export class VersionsModule {
}
