import {DifferenceInfo} from './difference-info';
import {UserInfo} from '../auth/user-info';
import {DifferenceType} from './difference-type.enum';

export class VersionInfo {
  differences: DifferenceInfo[];
  message: string;
  author: UserInfo;
  saveDate: Date;
  versionString: string;
  previousVersionString: string;

  get numberOfDifferences(): number {
    return this.differences.length || 0;
  }

  get numberOfModifiedLines(): number {
    return this.differences
      .filter(difference => difference.differenceType === DifferenceType.Modification)
      .map(difference => difference.newSectionSize)
      .reduce((a, b) => a + b, 0);
  }

  get numberOfInsertedLines(): number {
    return this.differences
      .filter(difference => difference.differenceType === DifferenceType.Insert)
      .map(difference => difference.newSectionSize)
      .reduce((a, b) => a + b, 0);
  }

  get numberOfDeletedLines(): number {
    return this.differences
      .filter(difference => difference.differenceType === DifferenceType.Delete)
      .map(difference => difference.previousSectionSize)
      .reduce((a, b) => a + b, 0);
  }
}
