import {Injectable} from "@angular/core";
import {NavigationEnd} from "@angular/router";

@Injectable()
export class DefaultService {
  public url: string = "";
  private _lastRoute: NavigationEnd = null;

  constructor() {
  }

  set lastRoute(value: NavigationEnd) {
    this._lastRoute = value;
  }
}
