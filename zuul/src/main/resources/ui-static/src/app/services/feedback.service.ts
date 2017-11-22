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
import {User} from "../domain/user";

@Injectable()
export class FeedbackService {
  private url: string = "/feedback/api/";

  constructor(private _http: Http, private _defaultService: DefaultService) {
    this.url = this._defaultService.url + this.url;
  }

  followers(id: string): Observable<User[]> {
    return this._http.get(this.url + "user/followers/" + id).map(it => it.json())
  }

  following(id: string): Observable<User[]> {
    return this._http.get(this.url + "user/following/" + id).map(it => it.json())
  }

  userInfo(id: string): Observable<UserInfo> {
    return this._http.get(this.url + "user/info/" + id).map(it => it.json());
  }

  mediaInfo(id: string): Observable<MediaInfo> {
    return this._http.get(this.url + "media/info/" + id).map(it => it.json());
  }

  comments(id: string): Observable<UserMediaComment[]> {
    return this._http.get(this.url + "media/comments/" + id).map(it => it.json());
  }

  like(id: string): Observable<UserMediaLike> {
    return this._http.post(this.url + "media/like/" + id, null).map(it => it.json());
  }

  liked(id: string): Observable<boolean> {
    return this._http.post(this.url + "media/liked/" + id, null).map(it => it.status == 200);
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

  followed(id: string): Observable<boolean> {
    return this._http.post(this.url + "user/followed/" + id, null).map(it => it.status == 200);
  }

  unfollow(id: string): Observable<Response> {
    return this._http.post(this.url + "user/unfollow/" + id, null);
  }

  comment(id: string, userMediaComment: UserMediaComment): Observable<UserMediaComment> {
    return this._http.post(this.url + "media/comment/" + id, userMediaComment).map(it => it.json());
  }

  uncomment(id: string): Observable<Response> {
    return this._http.post(this.url + "media/uncomment/" + id, null);
  }
}
