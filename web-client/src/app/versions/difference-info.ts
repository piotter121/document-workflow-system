import {DifferenceType} from './difference-type.enum';

export interface DifferenceInfo {
  previousSectionStart: number;
  previousSectionSize: number;
  newSectionStart: number;
  newSectionSize: number;
  differenceType: DifferenceType;
}
