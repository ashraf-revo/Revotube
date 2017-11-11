import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {DefaultService} from "./default.service";
import {Observable} from "rxjs/Rx";
import "rxjs/Rx";
import {User} from "../domain/user";

@Injectable()
export class UserService {
  private url: string = "/auth/";

  constructor(private _http: Http, private _defaultService: DefaultService) {
    this.url = this._defaultService.url + this.url;
  }

  currentUser(): Observable<User> {
    return this._http.get(this.url + "user").map(it => it.json().principal)
  }

  findOne(id: number): Observable<User> {
    return this._http.get(this.url + "api/user/" + id + "?fetchUserFeedBackInfo=true").map(it => it.json());
  }

  logout(): Observable<Response> {
    return this._http.get(this.url + "signout").flatMap(it => this._http.get(this._defaultService.url + "/signout"));
  }

}
