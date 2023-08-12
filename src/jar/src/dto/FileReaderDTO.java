package dto;

public class FileReaderDTO {
    boolean validFile;
    String error;

    public FileReaderDTO(boolean valid, String msg) {
        validFile = valid;
        error = msg;
    }

    public boolean isValid() { return validFile; }

    public String getError() { return error; }
}
