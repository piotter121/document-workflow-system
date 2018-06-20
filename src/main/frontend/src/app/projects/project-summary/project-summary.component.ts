import {Component, Input} from '@angular/core';
import {ProjectSummary} from "../project-summary";

@Component({
  selector: 'project-summary',
  templateUrl: './project-summary.component.html',
  styleUrls: ['./project-summary.component.css']
})
export class ProjectSummaryComponent {

  @Input() project: ProjectSummary;

  constructor() { }

}
