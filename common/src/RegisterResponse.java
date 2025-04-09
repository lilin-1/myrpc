import java.io.Serializable;

public class RegisterResponse implements Serializable {
    private boolean success;
    private String message;

    public RegisterResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}