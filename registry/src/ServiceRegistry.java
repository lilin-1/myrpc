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

            while (true) {
                Socket clientSocket = serverSocket.accept();
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
                RpcRequest request = (RpcRequest) input.readObject();

                // 查询服务地址
                String serviceAddress = serviceMap.get(request.getMethodName());

                // 构建响应
                RpcResponse response = new RpcResponse(
                        serviceAddress != null ?
                                serviceAddress :
                                "Service not found: " + request.getMethodName()
                );

                // 发送响应
                output.writeObject(response);
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

    // 测试用例
    public static void main(String[] args) throws IOException {
        ServiceRegistry registry = new ServiceRegistry(8500);

        // 注册示例服务
        registry.registerService("UserService", "192.168.1.100:9090");
        registry.registerService("OrderService", "192.168.1.101:9091");

        // 启动注册中心
        registry.start();
    }
}
