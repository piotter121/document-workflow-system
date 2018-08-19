import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TasksService} from '../tasks.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {map} from 'rxjs/operators';
import {NewTask} from '../new-task';
import {AppValidatorsService} from '../../shared/app-validators.service';
import {ToastNotificationService} from '../../shared/toast-notification.service';
import {RouteComponent} from "../../shared/route-component";

@Component({
  selector: 'add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit, RouteComponent {

  newTask: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private tasksService: TasksService,
              public route: ActivatedRoute,
              private router: Router,
              private appValidators: AppValidatorsService,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.newTask = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.maxLength(1000)]],
      administratorEmail: ['', [Validators.email, Validators.required], this.appValidators.existingUserEmail()]
    });
    this.route.paramMap
      .pipe(map((paramMap: ParamMap) => paramMap.get('projectId')))
      .subscribe((projectId: string) => this.name.setAsyncValidators(this.appValidators.nonExistingTaskNameInProject(projectId)));
  }

  get name(): AbstractControl {
    return this.newTask.get('name');
  }

  get description(): AbstractControl {
    return this.newTask.get('description');
  }

  get administratorEmail(): AbstractControl {
    return this.newTask.get('administratorEmail');
  }

  get projectId(): string {
    return this.route.snapshot.paramMap.get('projectId');
  }

  createTask() {
    let newTask: NewTask = {
      name: this.name.value,
      administratorEmail: this.administratorEmail.value,
      description: this.description.value
    };
    this.tasksService.createTask(newTask, this.projectId)
      .subscribe(
        (taskId: string) => this.router.navigate(['../', taskId]),
        () => this.toastNotification.error('dws.task.add.failure')
      );
  }
}
