import {Component, Input} from '@angular/core';
import {TaskSummary} from "../task-summary";

@Component({
  selector: 'task-summary',
  templateUrl: './task-summary.component.html',
  styleUrls: ['./task-summary.component.css']
})
export class TaskSummaryComponent {

  @Input()
  task: TaskSummary;

  constructor() {
  }

}
