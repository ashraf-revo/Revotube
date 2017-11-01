package org.revo.Controller;

import org.revo.Domain.Media;
import org.revo.Domain.Search;
import org.revo.Service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("search")
    private List<Media> search(@RequestBody Search search) throws IOException {
        return mediaService.search(search);
    }

}
