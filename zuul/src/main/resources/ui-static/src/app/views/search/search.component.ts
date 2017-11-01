import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {Search} from "../../domain/search";

@Component({
  selector: 'rt-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor(private _activatedRoute: ActivatedRoute) {
    this._activatedRoute.params.map((it: Params) => {
      let search = new Search();
      search.search_key = it['search_key'].split("-").join(" ");
      search.page = it['page'];
      return search;
    }).subscribe(it => {});

  }

  ngOnInit() {
  }

}
