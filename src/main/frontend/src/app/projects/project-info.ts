import {UserInfo} from "../auth/user-info";
import {TaskSummary} from "../tasks/task-summary";

export class ProjectInfo {
  id: string;
  name: string;
  description: string;
  administrator: UserInfo;
  creationDate: Date;
  lastModified: Date;
  tasks: TaskSummary[];
}
