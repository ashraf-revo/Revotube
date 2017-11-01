package org.revo.Config;

import org.revo.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Util {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return s -> userService.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    @Bean
    public CommandLineRunner runner(Env env, UserService userService) {
        return args -> env.getUsers().forEach(userService::save);
    }
}
