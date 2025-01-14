package cz.tieto.simcakry.notes.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException() {
      super("User with this email already exists");
    }
}
