import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProjectSummary} from '../project-summary';
import {ProjectsService} from '../projects.service';
import {GlobalsService} from '../../shared/globals.service';
import {ActivatedRoute} from '@angular/router';
import {ToastNotificationService} from '../../shared/toast-notification.service';

@Component({
  selector: 'all-projects',
  templateUrl: './all-projects.component.html',
  styleUrls: ['./all-projects.component.css']
})
export class AllProjectsComponent implements OnInit, OnDestroy {
  projects: ProjectSummary[] = [];

  constructor(private projectsService: ProjectsService,
              private globals: GlobalsService,
              private route: ActivatedRoute,
              private toastNotification: ToastNotificationService) {
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
      .subscribe((projects: ProjectSummary[]) => this.projects = projects,
        () => this.toastNotification.error('dws.project.all.failure'));
  }
}
