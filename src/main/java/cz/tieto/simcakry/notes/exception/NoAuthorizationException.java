package cz.tieto.simcakry.notes.exception;

public class NoAuthorizationException extends RuntimeException{
    public NoAuthorizationException() {
        super("You dont have permission for that action.");
    }
}
