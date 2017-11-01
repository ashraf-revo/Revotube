import {Injectable} from "@angular/core";
import {AuthUser} from "../domain/auth-user";
import {User} from "../domain/user";
@Injectable()
export class AuthService {
  private authUser: AuthUser;

  constructor() {
    this.authUser = new AuthUser(false, null);
  }

  setAuth(user: User, isAuth: boolean) {
    this.authUser.setData(isAuth, user);
  }

  getIsAuth(): boolean {
    return this.authUser.isAuth;
  }

   getAuthUser():AuthUser{
    return this.authUser;
  }
}
