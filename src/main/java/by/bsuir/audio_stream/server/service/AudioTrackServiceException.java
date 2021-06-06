package by.bsuir.audio_stream.server.service;

import java.io.Serial;

public class AudioTrackServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = -148626301319824700L;

    public AudioTrackServiceException() {
    }

    public AudioTrackServiceException(String message) {
        super(message);
    }

    public AudioTrackServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AudioTrackServiceException(Throwable cause) {
        super(cause);
    }
}
