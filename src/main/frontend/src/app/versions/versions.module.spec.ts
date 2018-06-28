import { VersionsModule } from './versions.module';

describe('VersionsModule', () => {
  let versionsModule: VersionsModule;

  beforeEach(() => {
    versionsModule = new VersionsModule();
  });

  it('should create an instance', () => {
    expect(versionsModule).toBeTruthy();
  });
});
