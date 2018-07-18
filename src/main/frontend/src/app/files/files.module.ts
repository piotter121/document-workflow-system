import {NgModule} from '@angular/core';
import {SharedModule} from "../shared/shared.module";
import {FileSummaryComponent} from './file-summary/file-summary.component';
import {FilesRoutingModule} from "./files-routing.module";
import {AddFileComponent} from './add-file/add-file.component';
import {FilesService} from "./files.service";

@NgModule({
  imports: [
    SharedModule,
    FilesRoutingModule
  ],
  declarations: [
    FileSummaryComponent,
    AddFileComponent
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
