import {Component, OnInit} from "@angular/core";
import {UserService} from "../../services/user.service";
import {TubeService} from "../../services/tube.service";
import {ActivatedRoute, Params} from "@angular/router";
import {User} from "../../domain/user";
import {Media} from "../../domain/media";
import {AuthService} from "../../services/auth.service";
import {FeedbackService} from "../../services/feedback.service";
import {AuthUser} from "../../domain/auth-user";

@Component({
  selector: 'rt-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public user: User;
  public authUser: AuthUser;
  public media: Media[] = [];
  public showClickFollow: boolean = false;
  public isFollow: boolean = false;
  public isAuth: boolean = false;
  public id: string;

  constructor(private _userService: UserService, private _tubeService: TubeService, private _activatedRoute: ActivatedRoute, private _authService: AuthService, private _feedbackService: FeedbackService) {

  }

  ngOnInit() {
    this.isAuth = this._authService.getIsAuth();
    this.authUser = this._authService.getAuthUser();
    this._activatedRoute.params.map((it: Params) => it['id'])
      .filter(it => this._authService.getIsAuth())
      .flatMap(it => this._feedbackService.followed(it))
      .subscribe(it => {
        console.log(it);
        this.isFollow = it;
      });

    this._activatedRoute.params.map((it: Params) => it['id']).subscribe(it => {
      this.id = it;
      this.showClickFollow = (this._authService.getIsAuth() == true) ? (this._authService.getAuthUser().user.id != it) : false;
    });

    this._activatedRoute.params.map((it: Params) => it['id']).flatMap(it => this._userService.findOne(it)).subscribe(it => {
      this.user = it;
    });
    this._activatedRoute.params.map((it: Params) => it['id']).flatMap(it => this._tubeService.findByUserId(it)).subscribe(it => {
      this.media = it;
    });
  }

  follow() {
    this._feedbackService.follow(this.id).subscribe(it => {
      this.user.userInfo.followers += 1;
      this.isFollow = true;
    })
  }

  unFollow() {
    this._feedbackService.unfollow(this.id).subscribe(it => {
      this.user.userInfo.followers -= 1;
      this.isFollow = false;
    })
  }

  followOrUnFollow() {
    if (this.isFollow) this.unFollow(); else this.follow();
  }

}
