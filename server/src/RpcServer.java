import java.lang.reflect.Method;
import java.net.*;
import java.io.*;


public class RpcServer {
    public static void export(String serviceName, Object serviceImpl, int port) throws IOException {
        // 注册服务到注册中心
        ServiceRegistry.registerService(serviceName, "localhost:" + port);

        // 创建ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        // 处理请求
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        RpcRequest request = (RpcRequest) input.readObject();

                        // 反射调用方法
                        Method method = serviceImpl.getClass().getMethod(
                                request.getMethodName(),
                                request.getParameterTypes());

                        Object result = method.invoke(serviceImpl, request.getParameters());

                        // 返回结果
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeObject(result);
                        output.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}