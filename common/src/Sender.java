import java.io.*;
import java.net.Socket;

public class Sender {
    private final String host;
    private final int port;

    public Sender(String serviceAddress) {
        String[] parts = serviceAddress.split(":");
        this.host = parts[0];
        this.port = Integer.parseInt(parts[1]);
    }

    public byte[] send(byte[] requestData) {
        try (Socket socket = new Socket(host, port)) {
            // 发送请求
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(requestData);
            outputStream.flush();

            // 获取响应
            InputStream inputStream = socket.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(data)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Socket communication failed", e);
        }
    }
}