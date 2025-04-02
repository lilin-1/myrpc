import java.net.*;
import java.io.*;

public class ClientDemo {
    public static void main(String [] args){
        //定义端口号8080
        int port=8080;
        try {
            //使用getByName来解析一个域名或者是ip地址的字符串，如果是以字节数组形式存储的ip地址则可以直接使用getByName
            InetAddress address=InetAddress.getByName("127.0.0.1");

            //创建一个客户端对象
            Socket client=new Socket(address,port);

            //获得输出到服务端的流
            OutputStream outToServer = client.getOutputStream();

            //Data包装，可以用writeUTF()直接写入字符串，不用手动转换。
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("我是c");

            //下面同理，直接合成一句
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("服务器响应：" + in.readUTF());

            //关闭连接
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
