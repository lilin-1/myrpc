import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.*;

public class ServiceRegistry {
    // 服务注册表（线程安全）
    private static final Map<String, String> serviceMap = new ConcurrentHashMap<>();

    // 服务端监听端口
    private final int port;

    // 线程池处理并发请求
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    public ServiceRegistry(int port) {
        this.port = port;
    }

    // 启动服务注册中心
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Service registry started on port " + port);


            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("begin");
                threadPool.execute(new ClientHandler(clientSocket));
            }

        }
    }

    // 处理客户端请求
    private class ClientHandler implements Runnable {
        private final Socket clientSocket;

        ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())
            ) {
                // 读取客户端请求
                Object request = input.readObject();

                // 处理注册请求
                if (request instanceof RegisterRequest) {
                    RegisterRequest registerRequest = (RegisterRequest) request;
                    String serviceName = registerRequest.getServiceName();
                    String serviceAddress = registerRequest.getServiceAddress();
                    serviceMap.put(serviceName, serviceAddress);
                    System.out.println("Registered service: " + serviceName + " -> " + serviceAddress);

                    // 发送响应
                    RegisterResponse response = new RegisterResponse(true, "Service registered successfully");
                    output.writeObject(response);
                }
                // 处理服务查询请求（原有逻辑）
                else if (request instanceof RpcRequest) {
                    RpcRequest rpcRequest = (RpcRequest) request;
                    String serviceAddress = serviceMap.get(rpcRequest.getMethodName());
                    RpcResponse rpcResponse = new RpcResponse(
                            serviceAddress != null ? serviceAddress : "Service not found: " + rpcRequest.getMethodName()
                    );
                    output.writeObject(rpcResponse);
                }
                output.flush();
            } catch (Exception e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }
    }

    // 服务注册方法
    public static void registerService(String serviceName, String address) {
        serviceMap.put(serviceName, address);
        System.out.println("Registered service: " + serviceName + " -> " + address);
    }

}
