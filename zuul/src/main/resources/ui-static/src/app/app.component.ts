import {Component, OnInit} from "@angular/core";
import {Router, Event, NavigationStart} from "@angular/router";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'rt-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  ngOnInit(): void {
    this._router.events.subscribe((it: Event) => {
      // console.log(it);
      if (it instanceof NavigationStart && this._authService.getIsAuth() == false && (it.url.indexOf("/settings") != -1 || it.url.indexOf("/upload") != -1)) {
        // this._router.navigate(['/'])
      }
    });
  }

  constructor(private _router: Router, private _authService: AuthService) {
  }

}
