import {FileSummary} from "../files/file-summary";

export class TaskSummary {
  id: string;
  name: string;
  creationDate: Date;
  lastModifiedFile: FileSummary;
  numberOfFiles: number;
  numberOfParticipants: number;
}
