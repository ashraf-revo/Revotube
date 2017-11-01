package org.revo.Service;

import org.revo.Domain.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;

/**
 * Created by ashraf on 23/04/17.
 */
@MessageEndpoint
public class Receiver {
    @Autowired
    private MediaService mediaService;

    @StreamListener(value = Processor.INPUT)
    public void receive(Message<Media> media) {
        mediaService.save(media.getPayload());
    }
}