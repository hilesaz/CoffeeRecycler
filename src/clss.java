import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Hilesaz
 * Date: 9/26/11
 * Time: 9:41 PM
 */
class field_info{
    short access_flags;
    short name_index;
    short descriptor_index;
    attribute_info[] attributes;

    /**
     * Do not allow instantiation of an empty field_info
     */
    protected  field_info()
    {}

    field_info(DataInputStream is)
    {
        try
        {
            access_flags = is.readShort();
            name_index = is.readShort();
            descriptor_index = is.readShort();
            attributes = new attribute_info[is.readShort()];
            for(attribute_info attr : attributes)
            {
                attr = new attribute_info(is);
            }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    boolean validate(cp_info[] constant_pool)
    {
        boolean valid = false;
        if(false/*TODO: Check access flags here */)
        {
            valid = false;
        }
        cp_info name = constant_pool[name_index];
        cp_info desc = constant_pool[descriptor_index];
        if(name.tag != cp_info.CONSTANT_Utf8 || !name.validate(constant_pool)
                || desc.tag != cp_info.CONSTANT_Utf8 || !desc.validate(constant_pool))
        {
            valid = false;
        }
        for(attribute_info attr : attributes)
        {
            if(!attr.validate(constant_pool))
            {
                valid = false;
            }
            String attribute_name = constant_pool[attr.attribute_name_index].getUtf8();
        }
        return valid;
    }
}
class method_info{
    short access_flags;
    short name_index;
    short descriptor_index;
    attribute_info[] attributes;

    /**
     * Do not allow instantiation of an empty method_info
     */
    protected method_info()
    {}

    /**
     * The ONLY way to make a method_info
     * @param is: to read method data from
     */
    method_info(DataInputStream is)
    {
        try
        {
            access_flags = is.readShort();
            name_index = is.readShort();
            descriptor_index = is.readShort();
            attributes = new attribute_info[is.readShort()];
            for(attribute_info attr : attributes)
            {
                attr = new attribute_info(is);
            }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param constant_pool: to validate against
     * @return true if method has valid attributes
     */
    boolean validate(cp_info[] constant_pool)
    {
        boolean valid = true;
        int countExceptionAttribute = 0;
        int countCodeAttribute = 0;
        for(attribute_info attr : attributes)
        {
            if(!attr.validate(constant_pool))
            {
                valid = false;
            }
            String attribute_name = constant_pool[attr.attribute_name_index].getUtf8();
            if(attribute_name.contentEquals("Code"))
            {
                countCodeAttribute++;
            }
            else if(attribute_name.contentEquals("Exceptions"))
            {
                countExceptionAttribute++;
            }
        }
        if(countCodeAttribute != 1 || countExceptionAttribute != 1)
        {
            valid = false;
        }
        return valid;
    }
}
class attribute_info{
    //TODO: maybe add a valid marker to cache validation?
    short attribute_name_index;
    byte[] info;
    public attribute_info(DataInputStream is)
    {
        try
        {
            attribute_name_index = is.readShort();
            info = new byte[is.readInt()];
            int lenRead = is.read(info);
            if(lenRead != info.length)
            {
                System.err.println("Couldn't finish reading attribute data.");
            }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
    boolean validate(cp_info[] constant_pool)
    {
        boolean valid = true;
        String attribute_name = constant_pool[attribute_name_index].getUtf8();
        return valid;
    }
}
public class clss {
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
        boolean first = true;
        for(cp_info constant_member : constant_pool)
        {
            if(first)
            {
                first = false;
                continue;
            }
            constant_member = cp_info.MakeConstant(is);
        }
    }
    public void printStats()
    {
        if(!validate())
        {
            System.err.println("Class file failed validation.");
        }
        System.out.println("Version" + major_ver + "." + minor_ver);
        System.out.println((constant_pool.length-1) + "Constant pool members.");
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
    public boolean validate()
    {
        boolean valid = true;
        for(cp_info cp_member : constant_pool)
        {
            if(!cp_member.validate(constant_pool))
            {
                valid = false;
            }
        }
        if(false/*TODO: Check access flags here */)
        {
            valid = false;
        }
        cp_info tc = constant_pool[this_class];
        if(super_class != 0)
        {
            cp_info sc = constant_pool[super_class];
            if(sc.tag != cp_info.CONSTANT_Class || !sc.validate(constant_pool))
            {
                valid = false;
            }
        }
        else
        {
            System.out.println("Why are you decompiling java.lang.object?");
        }
        if(tc.tag != cp_info.CONSTANT_Class || !tc.validate(constant_pool))
        {
            valid = false;
        }
        for(short interf : interfaces)
        {
            cp_info ic = constant_pool[interf];
            if(ic.tag != cp_info.CONSTANT_Class || !ic.validate(constant_pool))
            {
                valid = false;
            }
        }
        for(field_info field : fields)
        {
            if(!field.validate(constant_pool))
            {
                valid = false;
            }
        }
        for(method_info method : methods)
        {
            if(!method.validate(constant_pool))
            {
                valid = false;
            }
        }
        for(attribute_info attr : attributes)
        {
            if(!attr.validate(constant_pool))
            {
                valid = false;
            }
        }
        return valid;
    }
}
