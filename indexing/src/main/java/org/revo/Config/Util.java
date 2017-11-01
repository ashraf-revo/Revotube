package org.revo.Config;

import io.searchbox.client.JestClient;
import io.searchbox.indices.CreateIndex;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Util {
    @Bean
    public CommandLineRunner runner(JestClient client) {
        return args -> client.execute(new CreateIndex.Builder("media").build());
    }
}
