import java.net.*;
import java.io.*;

public class ClientServiceRegistry {
    // 新增注册中心地址配置
    private static final String REGISTRY_ADDRESS = "localhost:8500";

    public static String getServiceAddress(String serviceName, Class<?>[] parameterTypes, Object[] args) {
        try (Socket socket = new Socket(
                REGISTRY_ADDRESS.split(":")[0],
                Integer.parseInt(REGISTRY_ADDRESS.split(":")[1]))) {

            // 发送查询请求
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(new RpcRequest(serviceName,parameterTypes,args));
            output.flush();

            // 获取响应
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            RpcResponse response = (RpcResponse) input.readObject();
            return response.getServiceAddress();
        } catch (Exception e) {
            throw new RuntimeException("Failed to query registry", e);
        }
    }
}