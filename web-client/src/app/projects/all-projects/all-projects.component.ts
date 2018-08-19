import {Component, OnInit} from '@angular/core';
import {ProjectSummary} from '../project-summary';
import {ProjectsService} from '../projects.service';
import {ActivatedRoute} from '@angular/router';
import {ToastNotificationService} from '../../shared/toast-notification.service';
import {RouteComponent} from "../../shared/route-component";

@Component({
  selector: 'all-projects',
  templateUrl: './all-projects.component.html',
  styleUrls: ['./all-projects.component.css']
})
export class AllProjectsComponent implements OnInit, RouteComponent {
  projects: ProjectSummary[] = [];

  constructor(private projectsService: ProjectsService,
              public route: ActivatedRoute,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.loadProjects();
  }

  private loadProjects(): void {
    this.projectsService.getAllUserProjects()
      .subscribe((projects: ProjectSummary[]) => this.projects = projects,
        () => this.toastNotification.error('dws.project.all.failure'));
  }
}
