import {FileSummary} from "../files/file-summary";
import {Formatter} from "../shared/formatter";

export class ProjectSummary {
  id: string;
  name: string;
  creationDate: Date;

  get creationDateFormatted(): string {
    return Formatter.formatDateTime(this.creationDate);
  }

  lastModifiedFile: FileSummary;
  numberOfParticipants: number;
  numberOfTasks: number;
  numberOfFiles: number;
}
