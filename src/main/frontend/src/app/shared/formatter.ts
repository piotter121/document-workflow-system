import * as moment from "moment";

export const DATE_TIME_FORMAT: string = 'DD.MM.YYYY H:mm';

export class Formatter {

  static formatDateTime(date: Date): string {
    return moment(date).format(DATE_TIME_FORMAT);
  }
}
