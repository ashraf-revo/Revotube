import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {TubeService} from "../../services/tube.service";
import {Media} from "../../domain/media";
import {FeedbackService} from "../../services/feedback.service";
import {AuthService} from "../../services/auth.service";


@Component({
  selector: 'rt-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css']
})
export class VideoComponent implements OnInit {
  public id: number = -1;
  public m: Media;

  constructor(private _activatedRoute: ActivatedRoute, private _tubeService: TubeService, private _feedBackService: FeedbackService, private _authService: AuthService) {
  }

  ngOnInit(): void {

    this._activatedRoute.params
      .map(it => it['id'])
      .subscribe(it => {
        this.id = it;
      });

    this._activatedRoute.params
      .map(it => it['id'])
      .do(it => {

        if (this._authService.getIsAuth()) this._feedBackService.view(it).subscribe();

      })
      .flatMap(it => this._tubeService.findOne(it))
      .subscribe(it => {
        this.m = it;
      });

  }
}
