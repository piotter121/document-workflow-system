import {Component, Input, OnInit} from '@angular/core';
import {FileMetadata} from '../file-metadata';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'file-summary',
  templateUrl: './file-summary.component.html',
  styleUrls: ['./file-summary.component.css']
})
export class FileSummaryComponent implements OnInit {

  @Input()
  file: FileMetadata;

  lastModifiedMessageParams: any;

  constructor(private datePipe: DatePipe) {
  }

  ngOnInit() {
    if (!this.hasOneVersion()) {
      this.lastModifiedMessageParams = {
        modificationDate: this.datePipe.transform(this.file.modificationDate, 'dd.MM.yyyy HH:mm'),
        author: this.file.latestVersion.author
      };
    }
  }

  private hasOneVersion() {
    return this.file.creationDate === this.file.latestVersion.saveDate;
  }
}
