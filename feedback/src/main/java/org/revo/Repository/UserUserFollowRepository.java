package org.revo.Repository;

import org.revo.Domain.UserUserFollow;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserUserFollowRepository extends MongoRepository<UserUserFollow, String> {

    int countByFrom(String id);

    int countByTo(String current);

    void deleteByFromAndTo(String current, String id);

    Optional<UserUserFollow> findByFromAndTo(String current, String id);
}
