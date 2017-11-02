import {Component, Input, OnInit} from '@angular/core';
import {Media} from "../../domain/media";

@Component({
  selector: 'rt-video-row-search',
  templateUrl: './video-row-search.component.html',
  styleUrls: ['./video-row-search.component.css']
})
export class VideoRowSearchComponent implements OnInit {
  @Input()
  public media: Media;

  constructor() { }

  ngOnInit() {
  }

}
