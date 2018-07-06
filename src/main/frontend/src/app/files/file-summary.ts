import {Formatter} from "../shared/formatter";

export class FileSummary {
  name: string;
  saveDate: Date;
  get saveDateFormatted(): string {
    return Formatter.formatDateTime(this.saveDate);
  }
  author: string;
  taskName: string;
}
