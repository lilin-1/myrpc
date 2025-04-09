import java.io.*;

public class RpcResponse implements Serializable {
   private String serviceAddress;

    public RpcResponse(String s) {
        this.serviceAddress=s;
    }

    public String getServiceAddress() {
        return this.serviceAddress;
    }

}