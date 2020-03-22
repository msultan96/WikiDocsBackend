package com.infy.WikiDocsProject.Configuration;

import net.gjerull.etherpad.client.EPLiteClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EtherPadConfig {

    @Bean
    public EPLiteClient epLiteClient(){
        /*
            TODO: Change to read etherPadUrl and apiKey from cloud configuration
         */
        String etherPadUrl = "http://localhost:9001";
        String apiKey = "39745be536ed4fbdb3c805fe66d0b955a58681b34a4d6f1f710c52624104e311";
        return new EPLiteClient(etherPadUrl, apiKey);
    }
}
