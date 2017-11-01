package org.revo.Config;

import org.revo.Util.Hls;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by ashraf on 23/04/17.
 */
@Configuration
public class Util {
    @Bean
    CommandLineRunner runner() {
        return args -> Hls.init(new ClassPathResource("Bento4").getFile());
    }
}
