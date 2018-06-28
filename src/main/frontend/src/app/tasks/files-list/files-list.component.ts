import {Component, Input, OnInit} from '@angular/core';
import {TaskInfo} from "../task-info";

@Component({
  selector: 'files-list',
  templateUrl: './files-list.component.html',
  styleUrls: ['./files-list.component.css']
})
export class FilesListComponent implements OnInit {

  @Input()
  task: TaskInfo;

  constructor() {}

  ngOnInit() {
  }

}
