export class UserInfo {
  firstName: string;
  lastName: string;

  constructor(public email: string, public fullName?: string) {}
}
