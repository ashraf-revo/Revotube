import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Rx";
import "rxjs/Rx";
import {Http} from "@angular/http";
import {DefaultService} from "./default.service";
import {Media} from "../domain/media";
import {UserService} from "./user.service";

@Injectable()
export class TubeService {
  private url: string = "/tube/api/";


  constructor(private _http: Http, private _defaultService: DefaultService, private _userService: UserService) {
    this.url = this._defaultService.url + this.url;
  }


  findAll(): Observable<Media[]> {
    return this._http.get(this.url).map(it => it.json());
  }

  findByUserId(it: number): Observable<Media[]> {
    return this._http.get(this.url + "user/" + it).map(it => it.json());

  }
}
