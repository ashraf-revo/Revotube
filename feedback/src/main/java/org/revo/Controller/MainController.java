package org.revo.Controller;

import org.revo.Domain.*;
import org.revo.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by ashraf on 18/04/17.
 */
@RestController
@RequestMapping("api")
public class MainController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/user/info/{id}")
    public ResponseEntity<UserInfo> userInfo(@PathVariable("id") String id) {
        return ResponseEntity.ok(feedbackService.userInfo(id));
    }

    @GetMapping("/media/info/{id}")
    public ResponseEntity<MediaInfo> mediaInfo(@PathVariable("id") String id) {
        return ResponseEntity.ok(feedbackService.mediaInfo(id));
    }

    @GetMapping("/media/comments/{id}")
    public ResponseEntity<List<UserMediaComment>> comments(@PathVariable("id") String id) {
        return ResponseEntity.ok(feedbackService.comments(id));
    }

    @PostMapping("/media/like/{id}")
    public ResponseEntity<UserMediaLike> like(@PathVariable("id") String id) {
        return ResponseEntity.ok(feedbackService.like(id));
    }

    @PostMapping("/media/liked/{id}")
    public ResponseEntity liked(@PathVariable("id") String id) {
        return new ResponseEntity(feedbackService.liked(id) ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @PostMapping("/media/unlike/{id}")
    public ResponseEntity unlike(@PathVariable("id") String id) {
        feedbackService.unlike(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/media/view/{id}")
    public ResponseEntity<UserMediaView> view(@PathVariable("id") String id) {
        return ResponseEntity.ok(feedbackService.view(id));
    }

    @PostMapping("/user/follow/{id}")
    public ResponseEntity<UserUserFollow> follow(@PathVariable("id") String id) {
        return ResponseEntity.ok(feedbackService.follow(id));
    }

    @PostMapping("/user/unfollow/{id}")
    public ResponseEntity unfollow(@PathVariable("id") String id) {
        feedbackService.unfollow(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/media/comment/{id}")
    public ResponseEntity<UserMediaComment> comment(@PathVariable("id") String id, @RequestParam("message") String message) {
        return ResponseEntity.ok(feedbackService.comment(id, message));
    }

    @PostMapping("/media/uncomment/{id}")
    public ResponseEntity uncomment(@PathVariable("id") String id) {
        feedbackService.uncomment(id);
        return ResponseEntity.noContent().build();
    }
}