public class Test {
    public static void main(String[] args){
        // 创建客户端代理
        HelloService client = RpcProxy.create(HelloService.class);

        // 调用远程方法
        String result = client.sayHello("World");
        System.out.println(result); // 输出: Hello World
    }
}
