import java.io.Serializable;

public class RegisterRequest implements Serializable {
    private String serviceName;
    private String serviceAddress;

    public RegisterRequest(String serviceName, String serviceAddress) {
        this.serviceName = serviceName;
        this.serviceAddress = serviceAddress;
    }

    // Getters and Setters
    public String getServiceName() { return serviceName; }
    public String getServiceAddress() { return serviceAddress; }
}