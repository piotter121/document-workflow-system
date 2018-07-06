import {NgModule} from '@angular/core';
import {SharedModule} from "../shared/shared.module";
import { FileSummaryComponent } from './file-summary/file-summary.component';
import {FilesRoutingModule} from "./files-routing.module";

@NgModule({
  imports: [
    SharedModule,
    FilesRoutingModule
  ],
  declarations: [
    FileSummaryComponent
  ],
  exports: [
    FileSummaryComponent
  ]
})
export class FilesModule {
}
