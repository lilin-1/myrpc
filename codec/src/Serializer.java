public interface Serializer {
    //序列化方法
    <T> byte serialize(T obj);
    //反序列化方法
    <T> T deserialize(byte[] data, Class<T> clazz);
}
