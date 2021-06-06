package by.bsuir.audio_stream.server.service;

import by.bsuir.audio_stream.server.model.AudioTrack;
import by.bsuir.audio_stream.server.repository.AudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class AudioTrackServiceImpl implements AudioTrackService {

    private final AudioRepository audioRepository;

    @Value(value = "${audio.repository.path}")
    private String audioRepositoryPath;

    @Autowired
    public AudioTrackServiceImpl(@NotNull AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }

    @Override
    public Optional<AudioTrack> findById(long id) {
        Optional<AudioTrack> optionalAudioTrack = audioRepository.findById(id);
        return optionalAudioTrack;
    }

    @Override
    public List<AudioTrack> searchFor(String query, int limit) throws IllegalArgumentException {
        List<AudioTrack> audioTracks = audioRepository.searchAudioTracks(query, limit);
        return audioTracks;
    }

    @Override
    public Optional<InputStream> openInputStream(AudioTrack audioTrack) {
        File audioFile = new File(audioRepositoryPath + audioTrack.getFileName());
        try {
            FileInputStream audioFileInputStream = new FileInputStream(audioFile);
            return Optional.of(audioFileInputStream);
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public long defineFileSize(AudioTrack audioTrack) {
        File audioTrackFile =  new File(audioRepositoryPath + audioTrack.getFileName());
        long fileSize = audioTrackFile.length();
        return fileSize;
    }
}
