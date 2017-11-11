package org.revo.Service;

import org.revo.Domain.Ids;
import org.revo.Domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by ashraf on 22/04/17.
 */
@FeignClient("auth")
public interface UserFeignService {
    @PostMapping("/auth/api/users")
    List<User> users(@RequestBody Ids ids, @RequestParam(value = "fetchUserFeedBackInfo", required = false, defaultValue = "false") boolean fetchUserFeedBackInfo);
}
