import java.io.*;

public class RpcRequest implements Serializable {
    // 请求唯一标识（UUID或自增ID）
    private String requestId;
    // 目标接口名
    private String interfaceName;
    // 目标方法名
    private String methodName;
    // 方法参数类型（反射需要）
    private Class<?>[] parameterTypes;
    // 方法参数值
    private Object[] args;
}