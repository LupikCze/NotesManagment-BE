package cz.tieto.simcakry.notes.exception;

public class ShortPasswordException extends RuntimeException {
    public ShortPasswordException() {
        super("Password must be at least 7 characters long");
    }
}
