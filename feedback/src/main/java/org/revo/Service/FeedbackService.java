package org.revo.Service;

import org.revo.Domain.*;

import java.util.List;

public interface FeedbackService {
    List<UserUserFollow> followers(String id);

    List<UserUserFollow> following(String id);

    UserInfo userInfo(String id);

    MediaInfo mediaInfo(String id);

    List<UserMediaComment> comments(String id);

    UserMediaLike like(String id);

    void unlike(String id);

    UserMediaView view(String id);

    UserUserFollow follow(String id);

    void unfollow(String id);

    UserMediaComment comment(String id, String message);

    void uncomment(String id);

    boolean liked(String id);

    boolean followed(String id);
}
