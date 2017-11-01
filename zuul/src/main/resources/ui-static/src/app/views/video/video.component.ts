import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {TubeService} from "../../services/tube.service";
import {Media} from "../../domain/media";


@Component({
  selector: 'rt-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css']
})
export class VideoComponent implements OnInit {
  public id: number = -1;
  public m: Media;

  constructor(private _activatedRoute: ActivatedRoute, private _tubeService: TubeService) {
  }

  ngOnInit(): void {

    this._activatedRoute.params
      .map(it => it['id'])
      .subscribe(it => {
        this.id = it;
      });
    /*

     this._activatedRoute.params
     .map(it => it['id'])
     .flatMap(it=>this._tubeService.findMedia(it))
     .subscribe(it => {
     this.m = it;
     });
     */

  }
}
