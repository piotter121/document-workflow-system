import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";

const filesRoutes: Routes = [];

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
