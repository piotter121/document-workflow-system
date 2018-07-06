import {Component, Input, OnInit} from '@angular/core';
import {TaskInfo} from "../task-info";
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {AppValidatorsService} from "../../shared/app-validators.service";

@Component({
  selector: 'add-participant',
  templateUrl: './add-participant.component.html',
  styleUrls: ['./add-participant.component.css']
})
export class AddParticipantComponent implements OnInit {

  @Input()
  task: TaskInfo;
  isCollapsed: boolean = true;
  addParticipantForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private appValidators: AppValidatorsService
  ) {
  }

  ngOnInit() {
    this.addParticipantForm = this.formBuilder.group({
      participantEmail: [
        '',
        [Validators.required, this.nonExistingParticipant().bind(this)],
        this.appValidators.existingUserEmail()]
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
    // TODO write this method
  }
}
