import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {TubeService} from "../../services/tube.service";
import {Media} from "../../domain/media";
import {FeedbackService} from "../../services/feedback.service";
import {AuthService} from "../../services/auth.service";
import {UserMediaComment} from "../../domain/user-media-comment";


@Component({
  selector: 'rt-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css']
})
export class VideoComponent implements OnInit {
  public id: string = "-1";
  public m: Media;
  public isLiked: boolean = false;
  public isAuth: boolean = false;
  public commentText: string = '';
  public umc: UserMediaComment[] = [];
  public feedBackService: FeedbackService;
  public authService: AuthService;

  constructor(private _activatedRoute: ActivatedRoute, private _tubeService: TubeService, private _feedBackService: FeedbackService, private _authService: AuthService) {
    this.feedBackService = this._feedBackService;
    this.authService = _authService;
  }

  comments() {
    this.feedBackService.comments(this.id).subscribe(it => {
      this.umc = it;
    });
  }

  ngOnInit(): void {
    this.isAuth = this._authService.getIsAuth();
    this._activatedRoute.params
      .map(it => it['id'])
      .subscribe(it => {
        this.id = it;
      });

    this._activatedRoute.params
      .map(it => it['id'])
      .do(it => {
        if (this.isAuth) {
          this._feedBackService.view(it).subscribe();
          this.liked();
        }
        this.comments();

      })
      .flatMap(it => this._tubeService.findOne(it))
      .subscribe(it => {
        this.m = it;
      });
  }

  like() {
    if (this.isAuth) this._feedBackService.like(this.id).subscribe(it => {
      this.isLiked = true;
      this.m.mediaInfo.likes += 1;
    });
  }

  unlike() {
    if (this.isAuth) this._feedBackService.unlike(this.id).subscribe(it => {
      this.isLiked = false;
      this.m.mediaInfo.likes -= 1;
    });
  }

  likeOrUnLike() {
    if (this.isAuth) {
      if (this.isLiked) {
        this.unlike();
      } else {
        this.like()
      }
    }
  }

  liked() {
    this._feedBackService.liked(this.id).subscribe(it => {
      this.isLiked = it
    });
  }
}
