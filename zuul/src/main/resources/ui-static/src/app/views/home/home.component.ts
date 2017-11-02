import {Component, OnInit} from "@angular/core";
import {TubeService} from "../../services/tube.service";
import {Media} from "../../domain/media";

@Component({
  selector: 'rt-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public media: Media[] = [];

  constructor(private _tubeService: TubeService) {
  }

  ngOnInit() {
    this._tubeService.findAll().subscribe((it: Media[]) => this.media = it,error=>{},()=>{});
  }
}
