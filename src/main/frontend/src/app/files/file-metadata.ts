import {VersionInfo} from "../versions/version-info";
import {VersionSummary} from "../versions/version-summary";

export class FileMetadata {
  id: string;
  name: string;
  description: string;
  contentType: string;
  confirmed: boolean;
  markedToConfirm: boolean;
  creationDate: Date;
  modificationDate: Date;
  versions: VersionInfo[];
  extension: string;
  latestVersion: VersionSummary;
  numberOfVersions: number;
}
