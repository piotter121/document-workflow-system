import {UserInfo} from "../auth/user-info";
import {FileSummary} from "../files/file-summary";
import {FileMetadata} from "../files/file-metadata";

export class TaskInfo {
  id: string;
  name: string;
  description: string;
  projectId: string;
  administrator: UserInfo;
  creationDate: Date;
  modificationDate: Date;
  participants: UserInfo[];
  lastModifiedFile: FileSummary;
  filesInfo: FileMetadata[];
}
