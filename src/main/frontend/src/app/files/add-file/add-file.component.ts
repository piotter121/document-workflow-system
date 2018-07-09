import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-file',
  templateUrl: './add-file.component.html',
  styleUrls: ['./add-file.component.css']
})
export class AddFileComponent implements OnInit {

  newFile: FormGroup;

  constructor(
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit() {
    this.newFile = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(255)]],
      description: ['', Validators.maxLength(1024)],
      file: ['', Validators.required],
      versionString: ['1', [Validators.required, Validators.maxLength(20)]]
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

  }
}
