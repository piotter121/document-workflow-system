import { Component, OnInit } from '@angular/core';
import {ProjectSummary} from "../project-summary";
import {ProjectsService} from "../projects.service";

@Component({
  selector: 'app-all-projects',
  templateUrl: './all-projects.component.html',
  styleUrls: ['./all-projects.component.css']
})
export class AllProjectsComponent implements OnInit {
  projects: ProjectSummary[];

  constructor(private projectsService: ProjectsService) {}

  ngOnInit() {
    this.loadProjects();
  }

  private loadProjects(): void {
    this.projectsService.getAllUserProjects()
      .subscribe((projects: ProjectSummary[]) => {
        this.projects = projects;
      }, (error: any) => {
        console.error(error);
      });
  }

  trackByProjects(index: number, project: ProjectSummary): string {
    return project.id;
  }
}
