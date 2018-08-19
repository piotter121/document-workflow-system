import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ProjectsService} from '../projects.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastNotificationService} from '../../shared/toast-notification.service';
import {RouteComponent} from "../../shared/route-component";

@Component({
  selector: 'add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit, RouteComponent {

  newProject: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private projectsService: ProjectsService,
              private router: Router,
              public route: ActivatedRoute,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.newProject = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(40)]],
      description: ['', Validators.maxLength(1024)]
    });
  }

  get name(): AbstractControl {
    return this.newProject.get('name');
  }

  get description(): AbstractControl {
    return this.newProject.get('description');
  }

  createProject() {
    this.projectsService.createProject({
      name: this.name.value,
      description: this.description.value
    }).subscribe(
      projectId => this.router.navigate(['../', projectId], {
        relativeTo: this.route
      }),
      () => this.toastNotification.error('dws.project.add.failure')
    );
  }
}
