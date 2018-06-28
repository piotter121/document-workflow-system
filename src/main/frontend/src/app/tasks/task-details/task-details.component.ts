import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {TaskInfo} from "../task-info";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {TasksService} from "../tasks.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {TranslateService} from "@ngx-translate/core";
import {ErrorMessage} from "../../shared/error-message";

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {
  task$: Observable<TaskInfo>;
  task: TaskInfo;

  constructor(
    private route: ActivatedRoute,
    private tasksService: TasksService,
    private router: Router,
    private toast: ToastrService,
    private translate: TranslateService
  ) {
  }

  ngOnInit() {
    this.task$ = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.tasksService
        .getTaskInfo(params.get('projectId'), params.get('taskId')))
    );
    this.task$.subscribe((task: TaskInfo) => {
      this.task = task;
    }, (error: HttpErrorResponse) => this.onGetTaskError(error));
  }

  private onGetTaskError(error: HttpErrorResponse) {
    this.router.navigate(['..', '..'], {
      relativeTo: this.route
    }).then(() => {
      let errorMessage: ErrorMessage = error.error;
      if (errorMessage && errorMessage.errorCode)
        this.showErrorMessage(errorMessage);
      else
        console.log(error);
    });
  }

  private showErrorMessage(errorMessage: ErrorMessage) {
    this.translate.get(`dws.httpErrors.${errorMessage.errorCode}`, errorMessage.params)
      .subscribe((translation: string) => this.toast.error(translation));
  }
}
