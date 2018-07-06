import {Component, Input, OnInit} from '@angular/core';
import {ProjectSummary} from "../project-summary";
import {TranslateService} from "@ngx-translate/core";
import {Observable} from "rxjs";
import {Formatter} from "../../shared/formatter";

@Component({
  selector: 'project-summary',
  templateUrl: './project-summary.component.html',
  styleUrls: ['./project-summary.component.css']
})
export class ProjectSummaryComponent implements OnInit {

  @Input() project: ProjectSummary;

  creationDateMsg: Observable<string | any>;
  lastModifiedMsg: Observable<string | any>;

  constructor(private translateService: TranslateService) {
  }

  ngOnInit() {
    this.creationDateMsg = this.translateService.get('dws.project.summary.creationDate', {
      creationDate: Formatter.formatDateTime(this.project.creationDate)
    });
    if (this.project.lastModifiedFile) {
      let lastModifiedFile = this.project.lastModifiedFile;
      this.lastModifiedMsg = this.translateService.get('dws.project.summary.lastModifiedFile', {
        name: lastModifiedFile.name,
        saveDate: Formatter.formatDateTime(lastModifiedFile.saveDate),
        author: lastModifiedFile.author,
        taskName: lastModifiedFile.taskName
      });
    }
  }
}
