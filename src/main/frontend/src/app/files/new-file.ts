export class NewFile {
  // noinspection JSUnusedGlobalSymbols
  public versionMessage: string = "Dodanie pliku";
  constructor(
    public name: string,
    public description: string,
    public file: File,
    public versionString: string
  ) {
  }
}
