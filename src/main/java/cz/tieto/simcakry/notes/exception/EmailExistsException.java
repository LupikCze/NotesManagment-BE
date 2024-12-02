package cz.tieto.simcakry.notes.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
      super(message);
    }
}
