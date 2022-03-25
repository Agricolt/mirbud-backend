package pl.mirbudpol.sklepbudowlany.exceptions;

public class LackOfResources extends RuntimeException {
    public LackOfResources(String errorMessage) {
        super(errorMessage);
    }
}
