import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {TubeService} from "../../services/tube.service";
import {Media} from "../../domain/media";

@Component({
  selector: 'rt-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {
  public media: Media[] = [];

  constructor(private _authService: AuthService, private _tubeService: TubeService) {
  }

  ngOnInit() {
    this._tubeService.subscriptions().subscribe(it => this.media = it);
  }

}
