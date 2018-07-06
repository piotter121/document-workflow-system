import {Component, Input} from '@angular/core';
import {FileMetadata} from "../file-metadata";

@Component({
  selector: 'file-summary',
  templateUrl: './file-summary.component.html',
  styleUrls: ['./file-summary.component.css']
})
export class FileSummaryComponent {

  @Input()
  file: FileMetadata;

  constructor() {
  }

}
