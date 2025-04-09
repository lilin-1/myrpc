import java.io.*;

public interface Serializer {
    <T> byte[] serialize(T obj) throws IOException;
    <T> T deserialize(byte[] data, Class<T> clazz) throws IOException, ClassNotFoundException;
}
