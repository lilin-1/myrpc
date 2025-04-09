import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        ServiceRegistry serviceRegistry=new ServiceRegistry(8050);
        serviceRegistry.start();
    }
}
