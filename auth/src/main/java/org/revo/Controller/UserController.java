package org.revo.Controller;

import org.revo.Domain.Ids;
import org.revo.Domain.User;
import org.revo.Domain.UserInfo;
import org.revo.Service.FeedBackFeignService;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FeedBackFeignService feedBackFeignService;

    @GetMapping("/user/{id}")
    public User user(@PathVariable("id") String id, @RequestParam(value = "fetchUserFeedBackInfo", required = false, defaultValue = "false") boolean fetchUserFeedBackInfo) {
        User one = userService.findOne(id);
        if (!fetchUserFeedBackInfo)
            return one;
        else
            return (one == null) ? null : addUserFeedBackInfo((Arrays.asList(one))).get(0);
    }

    @PostMapping("/users")
    public Iterable<User> users(@RequestBody Ids ids, @RequestParam(value = "fetchUserFeedBackInfo", required = false, defaultValue = "false") boolean fetchUserFeedBackInfo) {
        List<User> all = StreamSupport.stream(userService.findAll(ids.getIds()).spliterator(), false).collect(toList());
        if (!fetchUserFeedBackInfo)
            return all;
        else
            return addUserFeedBackInfo(all);
    }


    private List<User> addUserFeedBackInfo(List<User> all) {
        Ids ids = new Ids();
        ids.setIds(all.stream().map(User::getId).collect(toList()));
        Map<String, UserInfo> collect = feedBackFeignService.userInfoByIds(ids).stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        return all.stream().map(it -> {
            it.setUserInfo(collect.get(it.getId()));
            return it;
        }).collect(toList());
    }
}
