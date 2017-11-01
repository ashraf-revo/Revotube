package org.revo.Service;

import org.revo.Domain.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

import java.io.IOException;

/**
 * Created by ashraf on 23/04/17.
 */
@MessageEndpoint
public class Receiver {
    @Autowired
    private Bento4Service bento4Service;

    @StreamListener(value = Processor.INPUT)
    @SendTo(value = Processor.OUTPUT)
    public Media receive(Message<Media> media) throws IOException {
        return bento4Service.convertHls(media.getPayload());
    }
}