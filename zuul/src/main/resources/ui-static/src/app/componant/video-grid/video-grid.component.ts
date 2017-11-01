import {Component, OnInit, Input} from "@angular/core";
import {Media} from "../../domain/media";

@Component({
  selector: 'rt-video-grid',
  templateUrl: './video-grid.component.html',
  styleUrls: ['./video-grid.component.css']
})
export class VideoGridComponent implements OnInit {
  @Input()
  media: Media;

  constructor() {
  }

  ngOnInit() {
  }

}
