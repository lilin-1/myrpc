import java.io.*;

public class RpcResponse implements Serializable {
   private String ServiceAddress;

    public RpcResponse(String s) {
    }

    public String getServiceAddress() {
        return ServiceAddress;
    }
}