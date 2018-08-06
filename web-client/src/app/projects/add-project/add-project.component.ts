import {Component, OnDestroy, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ProjectsService} from '../projects.service';
import {NewProject} from '../new-project';
import {ActivatedRoute, Router} from '@angular/router';
import {GlobalsService} from '../../shared/globals.service';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit, OnDestroy {

  newProject: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private projectsService: ProjectsService,
              private router: Router,
              private route: ActivatedRoute,
              private globals: GlobalsService) {}

  ngOnInit() {
    this.globals.route = this.route;
    this.newProject = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(40)]],
      description: ['', Validators.maxLength(1024)]
    });
  }

  ngOnDestroy() {
    this.globals.route = null;
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
