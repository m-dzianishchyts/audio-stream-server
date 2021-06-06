package by.bsuir.audio_stream.server.repository;

import by.bsuir.audio_stream.server.model.AudioTrack;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AudioRepositoryImpl implements AudioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AudioTrack> findById(long id) {
        Optional<AudioTrack> optionalAudioTrack = Optional.ofNullable(entityManager.find(AudioTrack.class, id));
        return optionalAudioTrack;
    }

    @Override
    public List<AudioTrack> searchAudioTracks(String query, int limit) throws IllegalArgumentException {

        List<AudioTrack> searchResult = entityManager
                .createQuery("SELECT a FROM AudioTrack a WHERE UPPER(a.title) LIKE UPPER(?1) OR UPPER(a.artist.name) LIKE UPPER(?1)",
                             AudioTrack.class)
                .setParameter(1, '%' + query + '%')
                .setMaxResults(limit).getResultList();
        return searchResult;
    }
}
