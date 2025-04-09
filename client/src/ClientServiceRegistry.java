import java.net.*;
import java.io.*;

public class ClientServiceRegistry {
    private static final String REGISTRY_ADDRESS = "localhost:8050";
    private final Sender registrySender;
    private final Serializer serializer = new SerializerImpl();

    public ClientServiceRegistry() {
        this.registrySender = new Sender(REGISTRY_ADDRESS);
    }

    public String getServiceAddress(String serviceName, Class<?>[] parameterTypes, Object[] args) {
        try {
            // 使用通用序列化器
            byte[] requestData = serializer.serialize(new RpcRequest(serviceName, parameterTypes, args));
            byte[] responseData = registrySender.send(requestData);
            RpcResponse response = serializer.deserialize(responseData, RpcResponse.class);

            return response.getServiceAddress();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to query registry", e);
        }
    }
}