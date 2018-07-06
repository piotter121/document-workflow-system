import {Component, Input, OnInit} from '@angular/core';
import {TaskInfo} from "../task-info";
import {UserService} from "../../auth/user.service";
import {UserInfo} from "../../auth/user-info";

@Component({
  selector: 'files-list',
  templateUrl: './files-list.component.html',
  styleUrls: ['./files-list.component.css']
})
export class FilesListComponent implements OnInit {

  @Input()
  task: TaskInfo;
  currentUser: UserInfo;

  constructor(private usersService: UserService) {
  }

  ngOnInit() {
    this.currentUser = this.usersService.currentUser;
  }

}
