import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FilesService} from '../files.service';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-add-file',
  templateUrl: './add-file.component.html',
  styleUrls: ['./add-file.component.css']
})
export class AddFileComponent implements OnInit {

  newFile: FormGroup;
  fileToUpload: File;
  private projectId: string;
  private taskId: string;

  constructor(
    private formBuilder: FormBuilder,
    private filesService: FilesService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastrService
  ) {
  }

  ngOnInit() {
    this.newFile = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(255)]],
      description: ['', Validators.maxLength(1024)],
      versionString: ['1', [Validators.required, Validators.maxLength(20)]]
    });
    this.route.paramMap.subscribe(paramMap => {
      this.projectId = paramMap.get('projectId');
      this.taskId = paramMap.get('taskId');
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
    this.filesService.addFileToTask(this.projectId, this.taskId, formData)
      .subscribe(
        (fileId: string) => this.router.navigate(['/projects', this.projectId, 'tasks', this.taskId, 'files', fileId]),
        (error: HttpErrorResponse) => this.toastService.error(error.message, error.name)
      );
  }
}
