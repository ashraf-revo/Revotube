package org.revo.Controller;

import org.revo.Domain.Ids;
import org.revo.Domain.User;
import org.revo.Domain.UserInfo;
import org.revo.Service.FeedBackFeignService;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * Created by ashraf on 10/04/17.
 */
@Controller
@SessionAttributes(types = AuthorizationRequest.class)
public class MainController {
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private FeedBackFeignService feedBackFeignService;

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(@ModelAttribute AuthorizationRequest clientAuth, Principal user) {
        return new ModelAndView("access_confirmation").addObject("auth_request", clientAuth)
                .addObject("client", clientDetailsService.loadClientByClientId(clientAuth.getClientId()));
    }

    @GetMapping("signup")
    public String modelAndView(User user) {
        return "signup";
    }

    @PostMapping("signup")
    public String modelAndView(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "signup";
        userService.encodeThenSave(user);
        return "redirect:/done?message=please+check+you+email+to+activate+your+account";
    }

    @GetMapping("activate/{id}")
    public String modelAndView(@PathVariable Long id) {
//        userService.activate(id);
        return "redirect:/done?message=successfully+activate+your+account+you+are+welcome";
    }

    @ResponseBody
    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @ResponseBody
    @GetMapping("/user/{id}")
    public User userById(@PathVariable("id") String id) {
        User one = userService.findOne(id);
        return (one == null) ? null : addUserInfo((Arrays.asList(one))).get(0);
    }

    @ResponseBody
    @PostMapping("/users")
    public Iterable<User> usersByIds(@RequestBody Ids ids) {
        List<User> all = StreamSupport.stream(userService.findAll(ids.getIds()).spliterator(), false).collect(toList());
        return addUserInfo(all);
    }


    private List<User> addUserInfo(List<User> all) {
        Ids ids = new Ids();
        ids.setIds(all.stream().map(User::getId).collect(toList()));
        Map<String, UserInfo> collect = feedBackFeignService.userInfoByIds(ids).stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        return all.stream().map(it -> {
            it.setUserInfo(collect.get(it.getId()));
            return it;
        }).collect(toList());
    }

}
