package org.revo.Service.Impl;

import org.revo.Domain.User;
import org.revo.Repository.UserRepository;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by ashraf on 17/04/17.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void encodeThenSave(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        save(user);
    }

/*
    @Override
    public void activate(Long id) {
        userRepository.activate(id);
    }
*/

    @Override
    public User findOne(String id) {
        return userRepository.findOne(id);
    }

}
