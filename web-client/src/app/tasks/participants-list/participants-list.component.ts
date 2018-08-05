import {Component, Input, OnInit} from '@angular/core';
import {TaskInfo} from '../task-info';
import {UserService} from '../../auth/user.service';
import {UserInfo} from '../../auth/user-info';
import {TasksService} from '../tasks.service';
import {ToastNotificationService} from '../../shared/toast-notification.service';

@Component({
  selector: 'participants-list',
  templateUrl: './participants-list.component.html',
  styleUrls: ['./participants-list.component.css']
})
export class ParticipantsListComponent implements OnInit {

  @Input()
  task: TaskInfo;
  currentUser: UserInfo;

  get filteredParticipants(): UserInfo[] {
    return this.task.participants
      .filter(participant => !this.currentUser.equals(participant));
  }

  constructor(private usersService: UserService,
              private tasksService: TasksService,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.currentUser = this.usersService.currentUser;
  }

  removeParticipant(participant: UserInfo) {
    this.tasksService.removeParticipant(participant, this.task.projectId, this.task.id)
      .subscribe(participants => {
        this.task.participants = participants;
        this.toastNotification.success('dws.task.details.participants.list.deleteSuccessful', {
          'fullName': participant.fullName
        });
      });
  }
}
