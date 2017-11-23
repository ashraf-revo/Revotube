import {Injectable} from "@angular/core";
import {NavigationEnd} from "@angular/router";
import {AuthService} from "./auth.service";
import {UserService} from "./user.service";

@Injectable()
export class DefaultService {
  public url: string = "";
  private _lastRoute: NavigationEnd = null;
  private protectedUrl: string[] = ['settings', 'upload', 'subscriptions'];

  constructor() {
  }

  set lastRoute(value: NavigationEnd) {
    this._lastRoute = value;
  }

  isAccessible(userService: UserService, authService: AuthService): boolean {
    if (authService.getAuthUser().isAuth == null || authService.getAuthUser().isAuth == "true") {
      return true;
    } else if (authService.getAuthUser().isAuth == "false" && this.protectedUrl.indexOf(this._lastRoute.url.split("/")[1]) != -1) {
      return false;
    }
    return true;
  }

  get lastRoute(): NavigationEnd {
    return this._lastRoute;
  }
}
