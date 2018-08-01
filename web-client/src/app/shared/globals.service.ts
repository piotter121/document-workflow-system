import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {NavbarData} from '../navbar/navbar-data';

@Injectable()
export class GlobalsService {

  title: Subject<NavbarData>;

  constructor() {
    this.title = new Subject();
  }

}
