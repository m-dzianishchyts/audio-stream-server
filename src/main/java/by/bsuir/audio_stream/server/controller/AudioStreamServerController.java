package by.bsuir.audio_stream.server.controller;

import by.bsuir.audio_stream.common.AudioTrackDto;
import by.bsuir.audio_stream.server.model.AudioTrack;
import by.bsuir.audio_stream.server.service.AudioTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/audio")
public class AudioStreamServerController {

    private static final int SEARCH_RESULT_LIMIT = 100;
    private final AudioTrackService audioTrackService;

    @Autowired
    public AudioStreamServerController(@NotNull AudioTrackService audioTrackService) {
        this.audioTrackService = audioTrackService;
    }

    @GetMapping("/stream")
    public ResponseEntity<StreamingResponseBody> streamAudioTrackById(@RequestParam(value = "id") long trackId) {
        Optional<AudioTrack> optionalAudioTrack = audioTrackService.findById(trackId);
        if (optionalAudioTrack.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Audio track was not found in database.");
        }
        AudioTrack audioTrack = optionalAudioTrack.get();
        Optional<InputStream> optionalAudioFileInputStream = audioTrackService.openInputStream(audioTrack);
        if (optionalAudioFileInputStream.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                              "Audio file was not found on the server.");
        }
        InputStream audioFileInputStream = optionalAudioFileInputStream.get();
        StreamingResponseBody responseBody = audioFileInputStream::transferTo;
        return ResponseEntity.ok()
                             .contentType(MediaType.valueOf("audio/mpeg"))
                             .body(responseBody);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AudioTrackDto>> searchForAudioTracks(@RequestParam(name = "query") String query) {
        List<AudioTrack> audioTracks = audioTrackService.searchFor(query, SEARCH_RESULT_LIMIT);
        List<AudioTrackDto> audioTrackDtoList =
                audioTracks.parallelStream()
                           .map(audioTrack -> {
                               long fileSize = audioTrackService.defineFileSize(audioTrack);
                               return new AudioTrackDto(audioTrack.getId(),
                                                 audioTrack.getTitle(),
                                                 audioTrack.getArtist().getName(),
                                                 audioTrack.getGenre().getName(),
                                                 audioTrack.getDuration(),
                                                 fileSize);
                           })
                           .sorted().collect(Collectors.toList());
        HttpStatus responseStatus = audioTrackDtoList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(responseStatus).body(audioTrackDtoList);
    }
}
