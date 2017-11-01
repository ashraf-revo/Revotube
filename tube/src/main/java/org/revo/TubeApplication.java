package org.revo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.integration.support.MessageBuilder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoAuditing
@EnableBinding({Processor.class})
public class TubeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TubeApplication.class, args);
    }
}
