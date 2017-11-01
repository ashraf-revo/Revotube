import {Injectable} from "@angular/core";
import "rxjs/Rx";
import {Http, Response} from "@angular/http";
import {DefaultService} from "./default.service";
import {Observable} from "rxjs/Observable";
import {UserInfo} from "../domain/user-info";
import {MediaInfo} from "../domain/media-info";
import {UserMediaComment} from "../domain/user-media-comment";
import {UserMediaLike} from "../domain/user-media-like";
import {UserMediaView} from "../domain/user-media-view";
import {UserUserFollow} from "../domain/user-user-follow";

@Injectable()
export class FeedbackService {
  private url: string = "/feedback/api/";

  constructor(private _http: Http, private _defaultService: DefaultService) {
    this.url = this._defaultService.url + this.url;
  }

  userInfo(id: string): Observable<UserInfo> {
    return this._http.get(this.url + "user/info/" + id).map(it => it.json());
  }

  mediaInfo(id: string): Observable<MediaInfo> {
    return this._http.get(this.url + "media/info/" + id).map(it => it.json());
  }

  Comments(id: string): Observable<UserMediaComment[]> {
    return this._http.get(this.url + "media/comments/" + id).map(it => it.json());
  }

  like(id: string): Observable<UserMediaLike> {
    return this._http.post(this.url + "media/like/" + id, null).map(it => it.json());
  }

  unlike(id: string): Observable<Response> {
    return this._http.post(this.url + "media/unlike/" + id, null);
  }

  view(id: string): Observable<UserMediaView> {
    return this._http.post(this.url + "media/view/" + id, null).map(it => it.json());
  }

  follow(id: string): Observable<UserUserFollow> {
    return this._http.post(this.url + "user/follow/" + id, null).map(it => it.json());
  }

  unfollow(id: string): Observable<Response> {
    return this._http.post(this.url + "user/unfollow/" + id, null);
  }

  comment(id: string, message: string): Observable<UserMediaComment> {
    return this._http.post(this.url + "media/comment/" + id, {'message': message}).map(it => it.json());
  }

  uncomment(id: string): Observable<Response> {
    return this._http.post(this.url + "media/uncomment/" + id, null);
  }
}
