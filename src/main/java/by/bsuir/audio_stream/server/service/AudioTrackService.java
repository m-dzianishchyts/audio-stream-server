package by.bsuir.audio_stream.server.service;

import by.bsuir.audio_stream.server.model.AudioTrack;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface AudioTrackService {

    Optional<AudioTrack> findById(long id);

    List<AudioTrack> searchFor(String query, int limit) throws IllegalArgumentException;

    default List<AudioTrack> searchFor(String query) {
        return searchFor(query, Integer.MAX_VALUE);
    }

    Optional<InputStream> openInputStream(AudioTrack audioTrack);

    long defineFileSize(AudioTrack audioTrack);
}
