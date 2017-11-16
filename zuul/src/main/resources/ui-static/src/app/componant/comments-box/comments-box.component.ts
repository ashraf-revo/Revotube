import {Component, Input, OnInit} from '@angular/core';
import {FeedbackService} from "../../services/feedback.service";
import {UserMediaComment} from "../../domain/user-media-comment";

@Component({
  selector: 'rt-comments-box',
  templateUrl: './comments-box.component.html',
  styleUrls: ['./comments-box.component.css']
})
export class CommentsBoxComponent implements OnInit {
  @Input()
  public feedBackService: FeedbackService;
  public commentText: string = '';
  @Input()
  public userMediaComments: UserMediaComment[] = [];
  @Input()
  id: string;
  @Input()
  public isAuth: boolean = false;


  constructor() {
  }

  ngOnInit() {

  }

  comment() {
    if (this.isAuth&&this.commentText.trim().length != 0) {
      let umc: UserMediaComment = new UserMediaComment();
      umc.message = this.commentText;
      this.feedBackService.comment(this.id, umc).subscribe(it => {
        this.userMediaComments.push(it);
        this.commentText = "";
      });
    }
  }
}
