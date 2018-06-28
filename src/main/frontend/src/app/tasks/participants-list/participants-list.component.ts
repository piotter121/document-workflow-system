import {Component, Input, OnInit} from '@angular/core';
import {TaskInfo} from "../task-info";

@Component({
  selector: 'participants-list',
  templateUrl: './participants-list.component.html',
  styleUrls: ['./participants-list.component.css']
})
export class ParticipantsListComponent implements OnInit {

  @Input()
  task: TaskInfo;

  constructor() { }

  ngOnInit() {
  }

}
