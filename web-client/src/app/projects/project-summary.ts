import {FileSummary} from '../files/file-summary';

export interface ProjectSummary {
  id: string;
  name: string;
  creationDate: Date;
  lastModifiedFile: FileSummary;
  numberOfParticipants: number;
  numberOfTasks: number;
  numberOfFiles: number;
}
