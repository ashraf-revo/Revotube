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


  retrieve(): Observable<Media[]> {
    return this._http.get(this.url).flatMap(res => Observable.from(res.json()))
      .flatMap((itm: Media) => {
        return this._userService.findOne(itm.user).map(it => {
          itm.tempUser = it;
          return itm;
        })
      }).toArray();

  }

  search(media: Media): Observable<Media[]> {
    return this._http.post(this.url + "search", media).flatMap(res => Observable.from(res.json()))
      .flatMap((itm: Media) => {
        return this._userService.findOne(itm.user).map(it => {
          itm.tempUser = it;
          return itm;
        })
      }).toArray();
  }

  findByUser(it: number): Observable<Media[]> {
    return this._http.get(this.url + "user/" + it).flatMap(res => Observable.from(res.json()))
      .flatMap((itm: Media) => {
        return this._userService.findOne(itm.user).map(it => {
          itm.tempUser = it;
          return itm;
        })
      }).toArray();

  }

  findMedia(id: number): Observable<Media> {
    return this._http.get(this.url + id).map(it => it.json())
  }
}
