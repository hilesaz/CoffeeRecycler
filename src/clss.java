import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Hilesaz
 * Date: 9/26/11
 * Time: 9:41 PM
 */
public class clss {
    private class cp_info{}
    private class field_info{}
    private class method_info{}
    private class attribute_info{}
    short minor_ver;
    short major_ver;
    cp_info[] constant_pool;
    short access_flags;
    short this_class;
    short super_class;
    short[] interfaces;
    field_info[] fields;
    method_info[] methods;
    attribute_info[] attributes;

    public void parseConstantPool(DataInputStream is)
    {

    }
    public void printStats()
    {
        System.out.println("Version" + major_ver + "." + minor_ver);
        System.out.println(constant_pool.length + "Constant pool members.");
        System.out.println("");
    }
    clss(String path)
    {
        try
        {
            DataInputStream is = new DataInputStream(new FileInputStream(path));
            int magic = is.readInt();
            if(magic != 0xCAFEBABE)
            {
                System.err.println("Incorrect magic number:");
                System.err.println(magic);
            }
            minor_ver = is.readShort();
            major_ver = is.readShort();
            constant_pool = new cp_info[is.readShort()];
            parseConstantPool(is);
            access_flags = is.readShort();
            this_class = is.readShort();
            super_class = is.readShort();
            interfaces = new short[is.readShort()];
            fields = new field_info[is.readShort()];
            methods = new method_info[is.readShort()];
            attributes = new attribute_info[is.readShort()];
            printStats();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("File could not be opened.");
            System.err.println(e.getMessage());
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
