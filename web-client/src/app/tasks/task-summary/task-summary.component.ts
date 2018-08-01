import {Component, Input, OnInit} from '@angular/core';
import {TaskSummary} from '../task-summary';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'task-summary',
  templateUrl: './task-summary.component.html',
  styleUrls: ['./task-summary.component.css']
})
export class TaskSummaryComponent implements OnInit {

  @Input()
  task: TaskSummary;

  lastModifiedFileMessageParams: any;

  constructor(private datePipe: DatePipe) {
  }

  ngOnInit() {
    if (this.task.lastModifiedFile) {
      const lastModifiedFile = this.task.lastModifiedFile;
      this.lastModifiedFileMessageParams = {
        name: lastModifiedFile.name,
        saveDate: this.datePipe.transform(lastModifiedFile.saveDate, 'dd.MM.yyyy HH:mm'),
        author: lastModifiedFile.author
      };
    }
  }

}
