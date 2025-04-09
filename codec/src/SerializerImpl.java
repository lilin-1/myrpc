import java.io.*;


public class SerializerImpl implements Serializer {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            oos.flush();
            return bos.toByteArray();
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return clazz.cast(ois.readObject());
        }
    }
}
