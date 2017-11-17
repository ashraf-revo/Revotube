import {Component, Input, OnInit} from '@angular/core';
import {AuthUser} from "../../domain/auth-user";

@Component({
  selector: 'rt-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  @Input()
  public authUser: AuthUser;

  constructor() { }

  ngOnInit() {
  }

}
