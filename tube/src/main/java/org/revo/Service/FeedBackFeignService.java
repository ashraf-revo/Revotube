package org.revo.Service;

import org.revo.Domain.Ids;
import org.revo.Domain.MediaInfo;
import org.revo.Domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by ashraf on 22/04/17.
 */
@FeignClient("feedback")
public interface FeedBackFeignService {
    @PostMapping("/feedback/api/media/info")
    List<MediaInfo> mediaInfo(@RequestBody Ids ids);
}
