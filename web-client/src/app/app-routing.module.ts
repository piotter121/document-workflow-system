import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {environment} from "src/environments/environment";

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'projects',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: 'projects'
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes, {
      enableTracing: environment.routing.enableTracing
    })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
