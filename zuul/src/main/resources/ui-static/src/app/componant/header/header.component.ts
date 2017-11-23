import {Component, Input, OnInit} from "@angular/core";
import {AuthUser} from "../../domain/auth-user";
import {UserService} from "../../services/user.service";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'rt-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Input()
  public authUser: AuthUser;
  @Input()
  public userService: UserService;
  @Input()
  public authService: AuthService;
  @Input()
  public router: Router;
  @Input()
  public activatedRoute: ActivatedRoute;
  @Input()
  public search_key: string = "";

  constructor() {
  }

  ngOnInit() {

  }

  search() {
    if (this.search_key.length > 0) {
      this.router.navigate(['/search', '0', this.search_key.split(" ").join("-")])
    }
  }

  logout() {
    this.userService.logout().subscribe(it => {
      this.authService.setAuth(null, "false");
    });

  }
}
