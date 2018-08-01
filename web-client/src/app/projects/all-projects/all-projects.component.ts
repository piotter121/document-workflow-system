import {Component, OnInit} from '@angular/core';
import {ProjectSummary} from '../project-summary';
import {ProjectsService} from '../projects.service';
import {ToastrService} from 'ngx-toastr';
import {HttpErrorResponse} from '@angular/common/http';
import {GlobalsService} from '../../shared/globals.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-all-projects',
  templateUrl: './all-projects.component.html',
  styleUrls: ['./all-projects.component.css']
})
export class AllProjectsComponent implements OnInit {
  projects: ProjectSummary[];

  constructor(
    private projectsService: ProjectsService,
    private toastService: ToastrService,
    private globals: GlobalsService,
    private translate: TranslateService
  ) {
  }

  ngOnInit() {
    this.translate.get(['dws.appName', 'dws.project.header']).subscribe(translations => {
      this.globals.title.next({
        main: translations['dws.appName'],
        small: translations['dws.project.header']
      });
    });
    this.loadProjects();
  }

  private loadProjects(): void {
    this.projectsService.getAllUserProjects()
      .subscribe((projects: ProjectSummary[]) => {
        this.projects = projects;
      }, (error: HttpErrorResponse) => {
        this.toastService.error(error.message, error.name);
      });
  }
}
