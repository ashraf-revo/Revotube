import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {DefaultService} from "./default.service";
import {Observable, Subject, ReplaySubject} from "rxjs/Rx";
import "rxjs/Rx";
import {User} from "../domain/user";
import {AuthUser} from "../domain/auth-user";

@Injectable()
export class UserService {
  private url: string = "/auth/";
  private users: Subject<User> = new ReplaySubject<User>();
  private cachedUsers: number[] = [];

  constructor(private _http: Http, private _defaultService: DefaultService) {
    this.url = this._defaultService.url + this.url;
  }

  currentUser(): Observable<User> {
    return this._http.get(this.url + "user").map(it => it.json().principal)
  }

  findOne(id: number): Observable<User> {
    let user: Observable<User> = this._http.get(this.url + "user/" + id).map(it => it.json()).publishReplay().refCount();
    if (this.cachedUsers.indexOf(id) != -1) return this.users.filter(it => it.id == id).first();
    else {
      user.subscribe(it => {
        this.users.next(it);
        this.cachedUsers.push(it.id);
      });
      return user;
    }
  }

  logout(): Observable<Response> {
    return this._http.get(this.url + "signout").flatMap(it => this._http.get(this._defaultService.url + "/signout"));
  }

}
