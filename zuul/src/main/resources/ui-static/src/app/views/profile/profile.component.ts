import {Component, OnInit} from "@angular/core";
import {UserService} from "../../services/user.service";
import {TubeService} from "../../services/tube.service";
import {ActivatedRoute, Params} from "@angular/router";
import {User} from "../../domain/user";
import {Media} from "../../domain/media";

@Component({
  selector: 'rt-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public user: User;
  public media: Media[] = [];

  constructor(private _userService: UserService, private _tubeService: TubeService, private _activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this._activatedRoute.params.map((it: Params) => it['id']).flatMap(it => this._userService.findOne(it)).subscribe(it => {
      this.user = it;
    });
    this._activatedRoute.params.map((it: Params) => it['id']).flatMap(it => this._tubeService.findByUserId(it)).subscribe(it => {
      this.media = it;
    });
  }

}
