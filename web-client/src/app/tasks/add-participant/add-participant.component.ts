import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TaskInfo} from '../task-info';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {AppValidatorsService} from '../../shared/app-validators.service';
import {TasksService} from '../tasks.service';
import {UserInfo} from '../../auth/user-info';
import {ToastNotificationService} from '../../shared/toast-notification.service';

@Component({
  selector: 'add-participant',
  templateUrl: './add-participant.component.html',
  styleUrls: ['./add-participant.component.css']
})
export class AddParticipantComponent implements OnInit {

  @Input()
  task: TaskInfo;
  @Output()
  taskChange: EventEmitter<TaskInfo> = new EventEmitter<TaskInfo>();

  isCollapsed: boolean = true;
  addParticipantForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private appValidators: AppValidatorsService,
              private tasksService: TasksService,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.addParticipantForm = this.formBuilder.group({
      participantEmail: [
        '',
        [Validators.required, Validators.email, this.nonExistingParticipant().bind(this)],
        this.appValidators.existingUserEmail()
      ]
    });
  }

  private nonExistingParticipant(): ValidatorFn {
    return (control: AbstractControl) => {
      if (!this.task.participants)
        return null;
      return this.task.participants
        .map(participant => participant.email)
        .includes(control.value) ?
        {'isCurrentParticipant': true} : null;
    };
  }

  get participantEmail() {
    return this.addParticipantForm.get('participantEmail');
  }

  addParticipant() {
    this.tasksService.addParticipantToTask(this.task.projectId, this.task.id, this.participantEmail.value)
      .subscribe((participants: UserInfo[]) => {
        this.toastNotification.success('dws.task.details.participants.addParticipant.addSuccess', {
          'email': this.participantEmail.value
        });
        this.task.participants = participants;
        this.taskChange.emit(this.task);
        this.isCollapsed = true;
      });
  }
}
