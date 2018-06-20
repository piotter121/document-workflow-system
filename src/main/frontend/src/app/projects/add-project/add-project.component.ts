import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ProjectsService} from "../projects.service";
import {NewProject} from "../new-project";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit {

  newProject: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private projectsService: ProjectsService,
              private router: Router) {}

  ngOnInit() {
    this.newProject = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(40)]],
      description: ['', Validators.maxLength(1024)]
    });
  }

  isInvalid(control: AbstractControl): boolean {
    return control.invalid && (control.touched || control.dirty);
  }

  get name(): AbstractControl {
    return this.newProject.get('name');
  }

  get description(): AbstractControl {
    return this.newProject.get('description');
  }

  createProject() {
    this.projectsService.createProject(new NewProject(
      this.name.value, this.description.value
    )).subscribe(projectId => this.router.navigate(['/projects', projectId]));
  }
}
