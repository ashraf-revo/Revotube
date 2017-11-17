import {Component, Input, OnInit} from '@angular/core';
import {FeedbackService} from "../../services/feedback.service";
import {UserMediaComment} from "../../domain/user-media-comment";
import {AuthService} from "../../services/auth.service";
import {User} from "../../domain/user";
import {AuthUser} from "../../domain/auth-user";

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
  @Input()
  public authUser: AuthUser;

  constructor() {
  }

  ngOnInit() {

  }

  comment() {
    if (this.isAuth && this.commentText.trim().length != 0) {
      let umc: UserMediaComment = new UserMediaComment();
      umc.message = this.commentText;
      this.feedBackService.comment(this.id, umc).subscribe(it => {
        this.userMediaComments.push(it);
        this.commentText = "";
      });
    }
  }
}
