import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Rx";
import "rxjs/Rx";
import {Http} from "@angular/http";
import {DefaultService} from "./default.service";
import {UserService} from "./user.service";
import {SearchResult} from "../domain/searchResult";
import {Search} from "../domain/search";

@Injectable()
export class IndexingService {
  private url: string = "/indexing/api/";


  constructor(private _http: Http, private _defaultService: DefaultService, private _userService: UserService) {
    this.url = this._defaultService.url + this.url;
  }


  search(search: Search): Observable<SearchResult> {
    return this._http.post(this.url + "search", search).map(it => it.json())

  }
}
