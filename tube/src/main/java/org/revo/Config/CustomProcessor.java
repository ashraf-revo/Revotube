package org.revo.Config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;

public interface CustomProcessor extends Processor {
    String CUSTOM_OUTPUT = "custom_output";

    @Output("custom_output")
    MessageChannel custom_output();

}
