package domain;

public class ScoreStorageException extends Exception {
    public ScoreStorageException(String message) {
        super(message);
    }

    public ScoreStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}