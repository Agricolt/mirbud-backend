package pl.mirbudpol.sklepbudowlany.exceptions;

public class NoPermissions extends RuntimeException{
    public NoPermissions(String errorMessage) {
        super(errorMessage);
    }
}
