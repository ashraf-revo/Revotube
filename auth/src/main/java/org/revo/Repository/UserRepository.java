package org.revo.Repository;

import org.revo.Domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by ashraf on 17/04/17.
 */
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);

    void activate(Long id);

/*
    @Transactional
    @Modifying
    @Query("update User set enable=true ,type='110' where id=?1 and type='000' ")
    void activate(Long id);
*/
}
