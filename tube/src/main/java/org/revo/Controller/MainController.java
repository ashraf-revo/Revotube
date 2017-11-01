package org.revo.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.revo.Domain.Media;
import org.revo.Domain.Status;
import org.revo.Service.MediaService;
import org.revo.Util.UtilView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created by ashraf on 18/04/17.
 */
@RestController
@RequestMapping("api")
public class MainController {
    @Autowired
    private MediaService mediaService;

    @PostMapping("save")
    private Media save(@ModelAttribute Media media) throws IOException {
        return mediaService.process(media);
    }

    @PostMapping("search")
    private List<Media> search(@RequestBody Media media) {
        return mediaService.findAll(Status.SUCCESS);
    }

/*
    @PostMapping("publish")
    private Media publish(@RequestBody Media media, @AuthenticationPrincipal User user) {
        return mediaService.publish(media, user);
    }
*/

    @GetMapping
    @JsonView(UtilView.Media.class)
    public Iterable<Media> findAll() {
        return mediaService.findAll(Status.SUCCESS);
    }

    @GetMapping("user/{id}")
    @JsonView(UtilView.Media.class)
    public List<Media> findAllByUser(@PathVariable("id") String id) {
        return mediaService.findByUser(id, Status.SUCCESS);
    }

    @GetMapping("{id}")
    @JsonView(UtilView.Media.class)
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
