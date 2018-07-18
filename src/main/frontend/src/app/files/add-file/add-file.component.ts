import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FilesService} from "../files.service";
import {ActivatedRoute} from "@angular/router";
import {NewFile} from "../new-file";

@Component({
  selector: 'app-add-file',
  templateUrl: './add-file.component.html',
  styleUrls: ['./add-file.component.css']
})
export class AddFileComponent implements OnInit {

  newFile: FormGroup;
  private projectId: string;
  private taskId: string;

  constructor(
    private formBuilder: FormBuilder,
    private filesService: FilesService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.newFile = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(255)]],
      description: ['', Validators.maxLength(1024)],
      file: ['', Validators.required],
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

  get file() {
    return this.newFile.get('file');
  }

  get versionString() {
    return this.newFile.get('versionString');
  }

  createNewFile() {
    const newFile: NewFile = new NewFile(
      this.name.value, this.description.value, this.file.value, this.versionString.value
    );
    this.filesService.addFileToTask(this.projectId, this.taskId, newFile);
  }
}
