import {Component, Input, OnInit} from '@angular/core';
import {ProjectSummary} from "../project-summary";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'project-summary',
  templateUrl: './project-summary.component.html',
  styleUrls: ['./project-summary.component.css']
})
export class ProjectSummaryComponent implements OnInit {

  @Input() project: ProjectSummary;

  lastModifiedFileMessageParams: any;

  constructor(private datePipe: DatePipe) {
  }

  ngOnInit() {
    if (this.project.lastModifiedFile) {
      const lastModifiedFile = this.project.lastModifiedFile;
      this.lastModifiedFileMessageParams = {
        name: lastModifiedFile.name,
        saveDate: this.datePipe.transform(lastModifiedFile.saveDate, 'dd.MM.yyyy HH:mm'),
        author: lastModifiedFile.author,
        taskName: lastModifiedFile.taskName
      };
    }
  }
}
