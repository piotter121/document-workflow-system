import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TasksService} from "../tasks.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {map} from "rxjs/operators";
import {NewTask} from "../new-task";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit {

  newTask: FormGroup;
  projectId: string;

  constructor(
    private formBuilder: FormBuilder,
    private tasksService: TasksService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.newTask = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.maxLength(1024)]],
      administratorEmail: ['', [Validators.email]]
    });
    this.route.paramMap
      .pipe(map((paramMap: ParamMap) => paramMap.get('projectId')))
      .subscribe((projectId: string) => this.projectId = projectId);
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
    let newTask = new NewTask(this.name.value, this.administratorEmail.value, this.description.value);
    this.tasksService.createTask(newTask, this.projectId)
      .subscribe(
        (taskId: string) => this.router.navigate(['/projects', this.projectId, 'tasks', taskId]),
        (error: HttpErrorResponse) => console.error(error)
      );
  }

  // noinspection JSMethodCanBeStatic
  isInvalid(control: AbstractControl): boolean {
    return control.invalid && (control.touched || control.dirty);
  }
}
