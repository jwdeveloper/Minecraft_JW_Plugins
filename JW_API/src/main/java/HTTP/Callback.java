package HTTP;

public interface Callback<T>
{
    public  void Response(T data, String message, int code);
}
