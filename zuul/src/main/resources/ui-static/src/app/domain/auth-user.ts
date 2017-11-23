import {User} from "./user";
export class AuthUser {
  constructor(public isAuth: string=null, public user: User) {
  }

  setData(isAuth: string, user: User) {
    this.isAuth = isAuth;
    this.user = user;
  }
}
