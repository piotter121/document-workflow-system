import {Component, OnDestroy, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TasksService} from '../tasks.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {map} from 'rxjs/operators';
import {NewTask} from '../new-task';
import {HttpErrorResponse} from '@angular/common/http';
import {AppValidatorsService} from '../../shared/app-validators.service';
import {GlobalsService} from '../../shared/globals.service';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit, OnDestroy {

  newTask: FormGroup;
  projectId: string;

  constructor(
    private formBuilder: FormBuilder,
    private tasksService: TasksService,
    private route: ActivatedRoute,
    private router: Router,
    private appValidators: AppValidatorsService,
    private globals: GlobalsService
  ) {
  }

  ngOnInit() {
    this.globals.route = this.route;
    this.route.paramMap
      .pipe(map((paramMap: ParamMap) => paramMap.get('projectId')))
      .subscribe((projectId: string) => {
        this.projectId = projectId;
        this.newTask = this.formBuilder.group({
          name: [
            '',
            [Validators.required, Validators.maxLength(50)],
            this.appValidators.nonExistingTaskNameInProject(this.projectId).bind(this.appValidators)
          ],
          description: ['', [Validators.maxLength(1000)]],
          administratorEmail: ['', [Validators.email, Validators.required], this.appValidators.existingUserEmail()]
        });
      });
  }

  ngOnDestroy() {
    this.globals.route = null;
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

  createTask() {
    let newTask: NewTask = {
      name: this.name.value,
      administratorEmail: this.administratorEmail.value,
      description: this.description.value
    };
    this.tasksService.createTask(newTask, this.projectId)
      .subscribe(
        (taskId: string) => this.router.navigate(['/projects', this.projectId, 'tasks', taskId]),
        (error: HttpErrorResponse) => console.error(error)
      );
  }
}
