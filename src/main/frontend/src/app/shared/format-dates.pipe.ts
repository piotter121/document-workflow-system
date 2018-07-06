import { Pipe, PipeTransform } from '@angular/core';
import * as moment from "moment";

@Pipe({
  name: 'formatDates'
})
export class FormatDatesPipe implements PipeTransform {

  transform(value: any, format: string): any {
    let result = {};
    Object.keys(value).forEach(key => {
      result[key] = (value[key] instanceof Date) ? moment(value[key]).format(format) : value[key];
    });
    return result;
  }

}
