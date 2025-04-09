import java.io.IOException;

public class Test {
    public static void main(String[] args) throws Exception {
        // 启动服务端
        new Thread(() -> {
            try {
                RpcServer.export("HelloService", new HelloServiceImpl(), 8079);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}