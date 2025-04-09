import java.io.*;

public class RpcRequest implements Serializable {
    private String methodName;
    private Class<?>[] parameterTypes;
    private  Object[] parameters;

    public RpcRequest(String name, Class<?>[] parameterTypes, Object[] args) {
        this.methodName=name;
        this.parameterTypes=parameterTypes;
        this.parameters=args;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }


}