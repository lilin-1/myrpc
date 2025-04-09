import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceRegistry {
    private static final Map<String, String> serviceMap = new ConcurrentHashMap<>();

    public static void register(String serviceName, String serviceAddress) {
        serviceMap.put(serviceName, serviceAddress);
    }

    public static String getServiceAddress(String serviceName) {
        return serviceMap.get(serviceName);
    }
}