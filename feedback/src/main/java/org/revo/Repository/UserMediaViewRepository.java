package org.revo.Repository;

import org.revo.Domain.UserMediaView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMediaViewRepository extends MongoRepository<UserMediaView, String> {
    int countByMedia(String id);
}
