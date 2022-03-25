package pl.mirbudpol.sklepbudowlany.exceptions;

public class NotValidPhoneNumber extends RuntimeException {
    public NotValidPhoneNumber(String errorMessage) {
        super(errorMessage);
    }
}
