import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {GlobalsService} from '../../shared/globals.service';

@Component({
  selector: 'add-version',
  templateUrl: './add-version.component.html',
  styleUrls: ['./add-version.component.css']
})
export class AddVersionComponent implements OnInit, OnDestroy {

  constructor(private route: ActivatedRoute,
              private globals: GlobalsService) { }

  ngOnInit() {
    this.globals.route = this.route;
  }

  ngOnDestroy() {
    this.globals.route = null;
  }

}
