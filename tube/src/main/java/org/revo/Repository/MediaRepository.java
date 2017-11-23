package org.revo.Repository;

import org.revo.Domain.Media;
import org.revo.Domain.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ashraf on 15/04/17.
 */
public interface MediaRepository extends CrudRepository<Media, String> {
    Media findTopByOrderByIdDesc();

    List<Media> findByStatus(Status status);

    List<Media> findByStatusAndUserIdIn(Status status, List<String> ids);

    List<Media> findByUserIdAndStatus(String id, Status status);

/*
    @Modifying
    @Query("update  Media set status=1 where status=0 and id=?1 and user=?2")
    int publish(Long media, Long user);
*/
}
