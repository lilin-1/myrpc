import java.net.*;
import java.io.*;
import java.lang.reflect.Proxy;

public class RpcProxy {
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
                    // 获取服务地址
                    String serviceAddress = ServiceRegistry.getServiceAddress(interfaceClass.getName());
                    if (serviceAddress == null) {
                        throw new RuntimeException("Service not found");
                    }

                    // 建立Socket连接
                    try (Socket socket = new Socket(serviceAddress.split(":")[0],
                            Integer.parseInt(serviceAddress.split(":")[1]))) {
                        // 发送请求
                        RpcRequest request = new RpcRequest(
                                method.getName(),
                                method.getParameterTypes(),
                                args);

                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeObject(request);
                        output.flush();

                        // 获取响应
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        return input.readObject();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
 