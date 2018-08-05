import {FileSummary} from "../files/file-summary";

export interface TaskSummary {
  id: string;
  name: string;
  creationDate: Date;
  lastModifiedFile: FileSummary;
  numberOfFiles: number;
  numberOfParticipants: number;
}
