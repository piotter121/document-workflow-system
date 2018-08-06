import {DifferenceInfo} from './difference-info';
import {UserInfo} from '../auth/user-info';

export interface VersionInfo {
  differences: DifferenceInfo[];
  message: string;
  author: UserInfo;
  saveDate: Date;
  versionString: string;
  previousVersionString: string;
}
