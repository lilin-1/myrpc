import java.net.*;
import java.io.*;


public class RpcServer {
    //排队长度在创建时确定
    private int backlog;

    public RpcServer(){
        //默认为50

        this.backlog=50;
    }

    public RpcServer(int backlog){
        this.backlog=backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    //运行时制定端口
    public void run(int port){
        try{
            //新建一个服务器对象
            ServerSocket serverSocket =new ServerSocket(port,backlog);
            System.out.println("服务器启动，等待客户端连接...");

            while (true){
                //创建客户端对象
                Socket server= serverSocket.accept();
                // 接收客户端消息
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println("收到客户端消息：" + in.readUTF());

                // 发送响应
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("你好，客户端！我是服务器 " + server.getLocalSocketAddress());

                server.close();  // 关闭连接
            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String [] args){
        RpcServer myServer=new RpcServer();
        myServer.run(8080);
    }
}
