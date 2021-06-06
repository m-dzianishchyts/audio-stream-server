package by.bsuir.audio_stream.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AudioStreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioStreamServerApplication.class, args);
    }
}
