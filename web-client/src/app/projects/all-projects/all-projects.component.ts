import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProjectSummary} from '../project-summary';
import {ProjectsService} from '../projects.service';
import {ToastrService} from 'ngx-toastr';
import {HttpErrorResponse} from '@angular/common/http';
import {GlobalsService} from '../../shared/globals.service';
import {TranslateService} from '@ngx-translate/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-all-projects',
  templateUrl: './all-projects.component.html',
  styleUrls: ['./all-projects.component.css']
})
export class AllProjectsComponent implements OnInit, OnDestroy {
  projects: ProjectSummary[];

  constructor(
    private projectsService: ProjectsService,
    private toastService: ToastrService,
    private globals: GlobalsService,
    private translate: TranslateService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.globals.route = this.route;
    this.loadProjects();
  }

  ngOnDestroy() {
    this.globals.route = null;
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
