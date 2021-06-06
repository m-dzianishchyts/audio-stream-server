package by.bsuir.audio_stream.server.repository;

import by.bsuir.audio_stream.server.model.AudioTrack;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudioRepository {

    Optional<AudioTrack> findById(long id);

    List<AudioTrack> searchAudioTracks(String query, int limit) throws IllegalArgumentException;
}
