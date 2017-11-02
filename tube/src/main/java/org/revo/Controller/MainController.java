package org.revo.Controller;

import org.revo.Domain.Ids;
import org.revo.Domain.Media;
import org.revo.Domain.Status;
import org.revo.Domain.User;
import org.revo.Service.MediaService;
import org.revo.Service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @PostMapping("save")
    private Media save(@ModelAttribute Media media) throws IOException {
        return mediaService.process(media);
    }

    @GetMapping
    public Iterable<Media> findAll() {
        return addUserInfo(mediaService.findAll(Status.SUCCESS));
    }

    private List<Media> addUserInfo(List<Media> all) {
        Ids ids = new Ids();
        ids.setIds(all.stream().map(Media::getUserId).collect(toList()));
        Map<String, User> collect = userFeignService.usersByIds(ids).stream().collect(Collectors.toMap(User::getId, Function.identity()));
        return all.stream().map(it -> {
            it.setUser(collect.get(it.getUserId()));
            it.setSecret(null);
            it.setM3u8(null);
            return it;
        }).collect(toList());
    }

    @GetMapping("user/{id}")
    public List<Media> findAllByUser(@PathVariable("id") String id) {
        return addUserInfo(mediaService.findByUser(id, Status.SUCCESS));
    }

    @GetMapping("{id}")
    public Media findOne(@PathVariable("id") String id) {
        return mediaService.findOne(id);
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
}
