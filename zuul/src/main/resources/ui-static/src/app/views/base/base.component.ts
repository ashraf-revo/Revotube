import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";
import {AuthUser} from "../../domain/auth-user";
import {TubeService} from "../../services/tube.service";
import {Media} from "../../domain/media";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {DefaultService} from "../../services/default.service";

@Component({
  selector: 'rt-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css']
})
export class BaseComponent implements OnInit {
  authUser: AuthUser;
  public media: Media[] = [];
  public userService: UserService;
  public authService: AuthService;
  public router: Router;
  public activatedRoute: ActivatedRoute;
  public search_key: string = "";

  constructor(private _activatedRoute: ActivatedRoute, private _router: Router, private _userService: UserService, private _authService: AuthService, private _tubeService: TubeService, private _defaultService: DefaultService) {
    this.router = this._router;
    this.activatedRoute = this._activatedRoute;
    this.userService = _userService;
    this.authUser = _authService.getAuthUser();
    this.authService = _authService;


    this.router.events.subscribe(it => {
        if (it instanceof NavigationEnd && it.url.indexOf("/search") != -1) {
          let message = it.url.split("/");
          this.search_key = message[3].split("-").join(" ");
        }
        else this.search_key = "";
        if (it instanceof NavigationEnd) {
          this._defaultService.lastRoute = it;
        }
      }
    );

  }


  ngOnInit() {


    this._tubeService.findAll().subscribe((it: Media[]) => this.media = it, error => {
    }, () => {
    });

    this._userService.currentUser().subscribe(it => {
        this._authService.setAuth(it, true);
      }
      , it => {
        this._authService.setAuth(null, false);
        this.authUser = this._authService.getAuthUser()
      }, () => {
      });
  }

}
