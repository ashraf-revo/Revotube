import {User} from "./user";
export class AuthUser {
  constructor(public isAuth: boolean, public user: User) {
  }

  setData(isAuth: boolean, user: User) {
    this.isAuth = isAuth;
    this.user = user;
  }
}
