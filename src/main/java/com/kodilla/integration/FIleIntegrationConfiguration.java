package com.kodilla.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;


@Configuration
public class FIleIntegrationConfiguration {

    @Bean
    IntegrationFlow fileIntegrationFlow(FileReadingMessageSource fileAdapter,
                                        FileTransformer fileTransformer,
                                        FileWritingMessageHandler outputFileAdapter) {
        return IntegrationFlows.from(fileAdapter, config -> config.poller(Pollers.fixedDelay(100)))
                .transform(fileTransformer, "transformFile")
                .handle(outputFileAdapter)
                .get();
    }

    @Bean
    FileReadingMessageSource fileAdapter() {
        FileReadingMessageSource fileSource = new FileReadingMessageSource();
        fileSource.setDirectory(new File("data/input"));

        return fileSource;
    }

    @Bean
    FileTransformer fileTransformer() {
        return new FileTransformer();
    }

    @Bean
    FileWritingMessageHandler outputFileAdapter() throws Exception{
        File directory = new File("data/output");
        FileWritingMessageHandler handler = new FileWritingMessageHandler(directory);
        handler.setAppendNewLine(true);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setExpectReply(false);
        return handler;
    }
}
