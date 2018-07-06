import {Component, Input, OnInit} from '@angular/core';
import {TaskInfo} from "../task-info";
import {UserService} from "../../auth/user.service";
import {UserInfo} from "../../auth/user-info";

@Component({
  selector: 'participants-list',
  templateUrl: './participants-list.component.html',
  styleUrls: ['./participants-list.component.css']
})
export class ParticipantsListComponent implements OnInit {

  @Input()
  task: TaskInfo;
  currentUser: UserInfo;

  constructor(private usersService: UserService) {
  }

  ngOnInit() {
    this.currentUser = this.usersService.currentUser;
  }

}
