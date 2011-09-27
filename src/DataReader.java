import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Hilesaz
 * Date: 9/26/11
 * Time: 12:43 AM
 */
public class DataReader{
    DataReader(DataInputStream _host)
    {
        host = _host;
    }
    int read(byte[] b)
    {
        int value = 0;
        try{
            value = host.read(b);
        }
        catch(IOException e)
        {
            System.err.println("Some error reading from stream:");
            System.err.println(e.getMessage());
        }
        return value;
    }
    int readInt()
    {
        int value = 0;
        try{
        value = host.readInt();
        }
        catch(IOException e)
        {
            System.err.println("Some error reading from stream:");
            System.err.println(e.getMessage());
        }
        return value;
    }
    long readLong()
    {
        long value = 0;
        try{
        value = host.readLong();
        }
        catch(IOException e)
        {
            System.err.println("Some error reading from stream:");
            System.err.println(e.getMessage());
        }
        return value;
    }
    float readFloat()
    {
        float value = 0;
        try{
        value = host.readFloat();
        }
        catch(IOException e)
        {
            System.err.println("Some error reading from stream:");
            System.err.println(e.getMessage());
        }
        return value;
    }
    double readDouble()
    {
        double value = 0;
        try{
        value = host.readDouble();
        }
        catch(IOException e)
        {
            System.err.println("Some error reading from stream:");
            System.err.println(e.getMessage());
        }
        return value;
    }
    char readChar()
    {
        char value = 0;
        try{
        value = host.readChar();
        }
        catch(IOException e)
        {
            System.err.println("Some error reading from stream:");
            System.err.println(e.getMessage());
        }
        return value;
    }
    short readShort()
    {
        short value = 0;
        try{
        value = host.readShort();
        }
        catch(IOException e)
        {
            System.err.println("Some error reading from stream:");
            System.err.println(e.getMessage());
        }
        return value;
    }
    boolean readBool()
    {
        boolean value = false;
        try{
        value = host.readBoolean();
        }
        catch(IOException e)
        {
            System.err.println("Some error reading from stream:");
            System.err.println(e.getMessage());
        }
        return value;
    }
    String readString()
    {
        String value = "";
        try{
        value = host.readUTF();
        }
        catch(IOException e)
        {
            System.err.println("Some error reading from stream:");
            System.err.println(e.getMessage());
        }
        return value;
    }
    DataInputStream host;
}
