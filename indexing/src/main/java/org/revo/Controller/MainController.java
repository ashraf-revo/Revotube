package org.revo.Controller;

import org.revo.Domain.*;
import org.revo.Service.MediaService;
import org.revo.Service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("search")
    private SearchResult search(@RequestBody Search search) throws IOException {
        return SearchResult.builder().media(addUserInfo(mediaService.search(search))).search(search).build();
    }

    private List<Media> addUserInfo(List<Media> all) {
        Ids ids = new Ids();
        ids.setIds(all.stream().map(Media::getUserId).collect(toList()));
        Map<String, User> collect = userFeignService.usersByIds(ids).stream().collect(Collectors.toMap(User::getId, Function.identity()));
        return all.stream().map(it -> {
            it.setUser(collect.get(it.getUserId()));
            return it;
        }).collect(toList());
    }
}
