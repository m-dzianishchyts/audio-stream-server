package by.bsuir.audio_stream.server.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "audio.repository")
@Data
@Validated
public class AudioRepositoryProperties {

    @NotEmpty
    private String path;
}
