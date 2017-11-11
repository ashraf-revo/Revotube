package org.revo.Controller;

import org.revo.Domain.*;
import org.revo.Service.FeedBackFeignService;
import org.revo.Service.MediaService;
import org.revo.Service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private MediaService mediaService;
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private FeedBackFeignService feedBackFeignService;

    @PostMapping("save")
    private Media save(@ModelAttribute Media media) throws IOException {
        return mediaService.process(media);
    }

    @GetMapping
    public Iterable<Media> findAll() {
        List<Media> all = mediaService.findAll(Status.SUCCESS);
        return addMediaFeedBackInfo(addUser(all, false));
    }

    @GetMapping("user/{id}")
    public List<Media> findAllByUser(@PathVariable("id") String id) {
        List<Media> all = mediaService.findByUser(id, Status.SUCCESS);
        return addMediaFeedBackInfo(addUser(all, false));
    }

    @GetMapping("{id}")
    public Media findOne(@PathVariable("id") String id) {
        Media one = mediaService.findOne(id);
        return (one == null) ? null : addMediaFeedBackInfo(addUser(Arrays.asList(one), false)).get(0);
    }

    @GetMapping("{id}.m3u8/{id}.key/")
    public void findOneKey(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        byte[] secret = mediaService.findOne(id).getSecret();
        response.getOutputStream().write(secret);
        response.setContentType("application/pgp-keys");
        response.setHeader("Content-disposition", "attachment; filename=" + id + ".key");
    }

    @GetMapping("{id}.m3u8/")
    public void findOneM3u8(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        response.getWriter().print(mediaService.findOneParsed(id));
        response.setContentType("application/x-mpegURL");
        response.setHeader("Content-disposition", "attachment; filename=" + id + ".m3u8");
    }

    private List<Media> addUser(List<Media> all, boolean fetchUserFeedBackInfo) {
        Ids ids = new Ids();
        ids.setIds(all.stream().map(Media::getUserId).collect(toList()));
        Map<String, User> collect = userFeignService.users(ids, fetchUserFeedBackInfo).stream().collect(Collectors.toMap(User::getId, Function.identity()));
        return all.stream().map(it -> {
            it.setUser(collect.get(it.getUserId()));
            return it;
        }).collect(toList());
    }

    private List<Media> addMediaFeedBackInfo(List<Media> all) {
        Ids ids = new Ids();
        ids.setIds(all.stream().map(Media::getId).collect(toList()));
        Map<String, MediaInfo> collect = feedBackFeignService.mediaInfo(ids).stream().collect(Collectors.toMap(MediaInfo::getId, Function.identity()));

        return all.stream().map(it -> {
            it.setMediaInfo(collect.get(it.getId()));
            return it;
        }).collect(toList());
    }

}
