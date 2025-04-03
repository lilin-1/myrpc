import java.io.*;

public class RpcResponse implements Serializable {
    private String requestId;
    // 成功时返回结果
    private Object result;
    // 失败时返回错误信息
    private String errorMsg;
}