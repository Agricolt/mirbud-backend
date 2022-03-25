package pl.mirbudpol.sklepbudowlany.exceptions;

public class DuplicatedValueException extends RuntimeException{
    public DuplicatedValueException(String errorMessage) {
        super(errorMessage);
    }
}
