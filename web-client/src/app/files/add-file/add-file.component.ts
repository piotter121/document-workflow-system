import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FilesService} from '../files.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {ToastNotificationService} from '../../shared/toast-notification.service';
import {RouteComponent} from "../../shared/route-component";

@Component({
  selector: 'add-file',
  templateUrl: './add-file.component.html',
  styleUrls: ['./add-file.component.css']
})
export class AddFileComponent implements OnInit, RouteComponent {

  newFile: FormGroup;
  fileToUpload: File;

  constructor(private formBuilder: FormBuilder,
              private filesService: FilesService,
              public route: ActivatedRoute,
              private router: Router,
              private toastNotification: ToastNotificationService) {
  }

  ngOnInit() {
    this.newFile = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(255)]],
      description: ['', Validators.maxLength(1024)],
      versionString: ['1', [Validators.required, Validators.maxLength(20)]]
    });
  }

  get name() {
    return this.newFile.get('name');
  }

  get description() {
    return this.newFile.get('description');
  }

  get versionString() {
    return this.newFile.get('versionString');
  }

  createNewFile() {
    const formData: FormData = new FormData();
    formData.append('name', this.name.value);
    formData.append('description', this.description.value);
    formData.append('file', this.fileToUpload, this.fileToUpload.name);
    formData.append('versionString', this.versionString.value);
    const paramMap: ParamMap = this.route.snapshot.paramMap;
    this.filesService.addFileToTask(paramMap.get('projectId'), paramMap.get('taskId'), formData)
      .subscribe(
        (fileId: string) => this.router.navigate(['../', fileId], {
          relativeTo: this.route
        }),
        () => this.toastNotification.error('dws.files.add.failure')
      );
  }
}
