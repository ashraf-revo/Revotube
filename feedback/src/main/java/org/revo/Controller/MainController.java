package org.revo.Controller;

import org.revo.Domain.*;
import org.revo.Service.FeedbackService;
import org.revo.Service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


/**
 * Created by ashraf on 18/04/17.
 */
@RestController
@RequestMapping("api")
public class MainController {
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private UserFeignService userFeignService;

    @GetMapping("/user/info/{id}")
    public ResponseEntity<UserInfo> userInfo(@PathVariable("id") String id) {
        return ResponseEntity.ok(feedbackService.userInfo(id));
    }

    @PostMapping("/user/info")
    public ResponseEntity<List<UserInfo>> userssInfo(@RequestBody Ids ids) {
        return ResponseEntity.ok().body(ids.getIds().stream().map(it -> feedbackService.userInfo(it)).collect(toList()));
    }

    @PostMapping("/media/info")
    public ResponseEntity<List<MediaInfo>> mediasInfo(@RequestBody Ids ids) {
        return ResponseEntity.ok().body(ids.getIds().stream().map(it -> feedbackService.mediaInfo(it)).collect(toList()));
    }

    @GetMapping("/media/info/{id}")
    public ResponseEntity<MediaInfo> mediaInfo(@PathVariable("id") String id) {
        return ResponseEntity.ok(feedbackService.mediaInfo(id));
    }

    @GetMapping("/media/comments/{id}")
    public ResponseEntity<List<UserMediaComment>> comments(@PathVariable("id") String id) {
        return ResponseEntity.ok(addUser(feedbackService.comments(id)));
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


    @PostMapping("/user/followed/{id}")
    public ResponseEntity followed(@PathVariable("id") String id) {
        return new ResponseEntity(feedbackService.followed(id) ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @PostMapping("/user/unfollow/{id}")
    public ResponseEntity unfollow(@PathVariable("id") String id) {
        feedbackService.unfollow(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/media/comment/{id}")
    public ResponseEntity<UserMediaComment> comment(@PathVariable("id") String id, @RequestBody UserMediaComment userMediaComment) {
        UserMediaComment one = feedbackService.comment(id, userMediaComment.getMessage());
        return ResponseEntity.ok((one == null) ? null : addUser(Arrays.asList(one)).get(0));
    }

    @PostMapping("/media/uncomment/{id}")
    public ResponseEntity uncomment(@PathVariable("id") String id) {
        feedbackService.uncomment(id);
        return ResponseEntity.noContent().build();
    }

    private List<UserMediaComment> addUser(List<UserMediaComment> all) {
        Ids ids = new Ids();
        ids.setIds(all.stream().map(UserMediaComment::getUserId).collect(toList()));
        Map<String, User> collect = userFeignService.users(ids, false).stream().collect(Collectors.toMap(User::getId, Function.identity()));
        return all.stream().map(it -> {
            it.setUser(collect.get(it.getUserId()));
            return it;
        }).collect(toList());
    }

}