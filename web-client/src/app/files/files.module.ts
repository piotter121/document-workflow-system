import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {FileSummaryComponent} from './file-summary/file-summary.component';
import {FilesRoutingModule} from './files-routing.module';
import {AddFileComponent} from './add-file/add-file.component';
import {FilesService} from './files.service';
import {FileDetailsComponent} from './file-details/file-details.component';
import {VersionsModule} from '../versions/versions.module';

@NgModule({
  imports: [
    SharedModule,
    FilesRoutingModule,
    VersionsModule
  ],
  declarations: [
    FileSummaryComponent,
    AddFileComponent,
    FileDetailsComponent
  ],
  providers: [
    FilesService
  ],
  exports: [
    FileSummaryComponent
  ]
})
export class FilesModule {
}
