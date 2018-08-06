import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {FileSummaryComponent} from './file-summary/file-summary.component';
import {FilesRoutingModule} from './files-routing.module';
import {AddFileComponent} from './add-file/add-file.component';
import {FilesService} from './files.service';
import {FileDetailsComponent} from './file-details/file-details.component';

@NgModule({
  imports: [
    SharedModule,
    FilesRoutingModule
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
