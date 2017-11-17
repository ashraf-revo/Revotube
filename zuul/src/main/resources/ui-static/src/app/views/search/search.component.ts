import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {Search} from "../../domain/search";
import {Media} from "../../domain/media";
import {IndexingService} from "../../services/indexing.service";
import {Location} from '@angular/common';

@Component({
  selector: 'rt-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  public media: Media[] = [];
  public search: Search = new Search();

  constructor(private _activatedRoute: ActivatedRoute, private _indexingService: IndexingService, private _location: Location) {
  }

  more() {
    this.search.page += 1;
    this.doSearch()
  }

  ngOnInit() {
    this._activatedRoute.params.map((it: Params) => {
      let search = new Search();
      search.search_key = it['search_key'].split("-").join(" ");
      search.page = Number.parseInt(it['page']);
      return search;
    })
      .subscribe(it => {
        this.search = it;
        this.media = [];
        this.doSearch()
      });
  }

  doSearch() {
    this._indexingService.search(this.search).subscribe(it => {
      it.media.forEach(itm => {
        this.media.push(itm)
      });
      this._location.replaceState("/search/" + this.search.page + "/" + (this.search.search_key.split(" ").join("-")));
    });
  }
}
