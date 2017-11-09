package org.revo.Repository;

import org.revo.Domain.UserMediaLike;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMediaLikeRepository extends MongoRepository<UserMediaLike, String> {
    void deleteByMediaAndUserId(String id, String current);

    int countByMedia(String id);

    Optional<UserMediaLike> findByUserIdAndMedia(String user,String media);
}
