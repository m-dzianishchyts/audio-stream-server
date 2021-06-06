package by.bsuir.audio_stream.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "audio_tracks")
public class AudioTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String title;

    @NotNull
    @ManyToOne
    private Artist artist;

    @ManyToOne
    private Genre genre;

    @NotNull
    private String fileName;

    @Min(0)
    private int duration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (AudioTrack) o;
        if (id != that.id) {
            return false;
        }
        if (duration != that.duration) {
            return false;
        }
        if (!title.equals(that.title)) {
            return false;
        }
        if (!artist.equals(that.artist)) {
            return false;
        }
        if (Objects.equals(genre, that.genre)) {
            return false;
        }
        return fileName.equals(that.fileName);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + artist.hashCode();
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + fileName.hashCode();
        result = 31 * result + duration;
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AudioTrack.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("artist=" + artist)
                .add("genre=" + genre)
                .add("fileName='" + fileName + "'")
                .add("duration=" + duration)
                .toString();
    }
}
