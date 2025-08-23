package ro.scoalainformala.studentmgmt.exceptions;

public class UploadFailedException extends RuntimeException {
    public UploadFailedException(String message) {
        super(message);
    }
}
