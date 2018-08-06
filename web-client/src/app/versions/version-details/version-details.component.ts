import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {VersionInfo} from '../version-info';
import {Observable} from 'rxjs';
import {switchMap} from 'rxjs/operators';
import {DiffData, VersionsService} from '../versions.service';
import {DifferenceType} from '../difference-type.enum';
import {GlobalsService} from '../../shared/globals.service';

@Component({
  selector: 'version-details',
  templateUrl: './version-details.component.html',
  styleUrls: ['./version-details.component.css']
})
export class VersionDetailsComponent implements OnInit, OnDestroy {

  version$: Observable<VersionInfo>;
  diffData$: Observable<DiffData>;

  constructor(private route: ActivatedRoute,
              private versionsService: VersionsService,
              private globals: GlobalsService) {
  }

  ngOnInit() {
    this.globals.route = this.route;
    this.version$ = this.route.paramMap.pipe(switchMap((paramMap: ParamMap) => {
      const projectId = paramMap.get('projectId');
      const taskId = paramMap.get('taskId');
      const fileId = paramMap.get('fileId');
      const versionId = paramMap.get('versionId');
      return this.versionsService.getVersionInfo(projectId, taskId, fileId, versionId);
    }));
    this.diffData$ = this.route.paramMap.pipe(switchMap((paramMap: ParamMap) => {
      const projectId = paramMap.get('projectId');
      const taskId = paramMap.get('taskId');
      const fileId = paramMap.get('fileId');
      const versionId = paramMap.get('versionId');
      return this.versionsService.getDiffData(projectId, taskId, fileId, versionId);
    }));
  }

  ngOnDestroy() {
    this.globals.route = null;
  }

  getClassForDifferenceInOldContent(diffData: DiffData, i: number): string {
    const differenceType = this.getDiffTypeForOldVersion(diffData, i);
    return VersionDetailsComponent.getClassForDifferenceType(differenceType);
  }

  static getClassForDifferenceType(differenceType: DifferenceType): string {
    if (!differenceType)
      return '';
    switch (differenceType) {
      case DifferenceType.Insert:
        return 'bg-success';
      case DifferenceType.Delete:
        return 'bg-danger';
      case DifferenceType.Modification:
        return 'bg-info';
      default:
        return '';
    }
  }

  getDiffTypeForOldVersion(diffData: DiffData, i: number): DifferenceType {
    const diff = diffData.differences
      .find(difference => difference.differenceType !== DifferenceType.Insert
        && difference.previousSectionSize !== 0
        && i > difference.previousSectionStart
        && i <= (difference.previousSectionStart + difference.previousSectionSize));
    return !!diff ? diff.differenceType : null;
  }

  getDiffTypeForNewVersion(diffData: DiffData, i: number): DifferenceType {
    const diff = diffData.differences
      .find(difference => difference.differenceType !== DifferenceType.Delete
        && difference.newSectionSize !== 0
        && i > difference.newSectionStart
        && i <= (difference.newSectionStart + difference.newSectionSize));
    return !!diff ? diff.differenceType : null;
  }

  getClassForDifferenceInNewContent(diffData: DiffData, i: number): string {
    const differenceType = this.getDiffTypeForNewVersion(diffData, i);
    return VersionDetailsComponent.getClassForDifferenceType(differenceType);
  }
}
