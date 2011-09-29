import java.io.DataInputStream;
import java.io.IOException;

class cp_info
{
    byte tag;
    byte[] info;

    //String
    public static final int CONSTANT_Utf8       = 1;
    //int
    public static final int CONSTANT_Integer    = 3;
    //float
    public static final int CONSTANT_Float      = 4;
    //long
    public static final int CONSTANT_Long       = 5;
    //double
    public static final int CONSTANT_Double     = 6;
    public class class_ref
    {
        short name_index;
    }
    public static final int CONSTANT_Class      = 7;
    public class string_ref
    {
        short Utf8_index;
    }
    public static final int CONSTANT_String     = 8;
    public class field_ref
    {
        short class_index;
        short name_and_type_index;
    }
    public static final int CONSTANT_Fieldref   = 9;
    //uses field_ref
    public static final int CONSTANT_Methodref  = 10;
    //uses field_ref
    public static final int CONSTANT_InterfaceMethodref = 11;
    public class name_and_type_ref
    {
        short name_index;
        short type_index;
    }
    public static final int CONSTANT_NameAndType        = 12;


    public static cp_info MakeConstant(DataInputStream is)
    {
        cp_info constant_member;
        byte tag;
        try
        {
            tag = is.readByte();
            switch(tag)
            {
                case CONSTANT_Utf8:
                {
                    constant_member = new Utf8_info();
                    constant_member.tag = tag;
                    short length = is.readShort();
                    constant_member.info = new byte[length];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Utf8-encoded string.  Data read: "
                                + new String(constant_member.info,0,lenRead));
                    }
                    break;
                }
                case CONSTANT_Integer:
                {
                    constant_member = new Integer_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[4];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Integer.  Data read: "
                                + constant_member.getInt());
                    }
                    break;
                }
                case CONSTANT_Float:
                {
                    constant_member = new Float_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[4];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Float.  Data read: "
                                + constant_member.getFloat());
                    }
                    break;
                }
                case CONSTANT_Long:
                {
                    constant_member = new Long_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[8];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Long.  Data read: "
                                + constant_member.getLong());
                    }
                    break;
                }
                case CONSTANT_Double:
                {
                    constant_member = new Double_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[8];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Double.  Data read: "
                                + constant_member.getDouble());
                    }
                    break;
                }
                case CONSTANT_Class:
                {
                    constant_member = new Class_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[2];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Class.  Data read: "
                                + constant_member.getClassRef().name_index);
                    }
                    break;
                }
                case CONSTANT_String:
                {
                    constant_member = new String_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[2];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read String reference.  Data read: "
                                + constant_member.getString().Utf8_index);
                    }
                    break;
                }
                case CONSTANT_Fieldref:
                {
                    constant_member = new Fieldref_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[4];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Field reference.  Data read: "
                                + constant_member.getField().class_index + ", "
                                + constant_member.getField().name_and_type_index);
                    }
                    break;
                }
                case CONSTANT_Methodref:
                {
                    constant_member = new Methodref_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[4];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Method reference.  Data read: "
                                + constant_member.getMethod().class_index + ", "
                                + constant_member.getMethod().name_and_type_index);
                    }
                    break;
                }
                case CONSTANT_InterfaceMethodref:
                {
                    constant_member = new InterfaceMethodref_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[4];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Interface Method reference.  Data read: "
                                + constant_member.getInterfaceMethod().class_index + ", "
                                + constant_member.getInterfaceMethod().name_and_type_index);
                    }
                    break;
                }
                case CONSTANT_NameAndType:
                {
                    constant_member = new NameAndType_info();
                    constant_member.tag = tag;
                    constant_member.info = new byte[4];
                    int lenRead = is.read(constant_member.info);
                    if(lenRead != constant_member.info.length)
                    {
                        throw new IOException("Couldn't fully read Name and Type.  Data read: "
                                + constant_member.getNameAndType().name_index + ", "
                                + constant_member.getNameAndType().type_index);
                    }
                    break;
                }
                default:
                {
                    throw new IOException("Invalid constant pool tag: " + tag);
                }
            }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
            constant_member = null;
        }
        return constant_member;
    }


    String getUtf8()
    {
        System.err.println("Not a CONSTANT_Utf8");
        return null;
    }
    int getInt()
    {
        System.err.println("Not a CONSTANT_Integer");
        return 0;
    }
    float getFloat()
    {
        System.err.println("Not a CONSTANT_Float");
        return 0;
    }
    long getLong()
    {
        System.err.println("Not a CONSTANT_Long");
        return 0;
    }
    double getDouble()
    {
        System.err.println("Not a CONSTANT_Double");
        return 0;
    }
    class_ref getClassRef()
    {
        System.err.println("Not a CONSTANT_Class");
        return null;
    }
    string_ref getString()
    {
        System.err.println("Not a CONSTANT_String");
        return null;
    }
    field_ref getField()
    {
        System.err.println("Not a CONSTANT_Fieldref");
        return null;
    }
    field_ref getMethod()
    {
        System.err.println("Not a CONSTANT_Methodref");
        return null;
    }
    field_ref getInterfaceMethod()
    {
        System.err.println("Not a CONSTANT_InterfaceMethodref");
        return null;
    }
    name_and_type_ref getNameAndType()
    {
        System.err.println("Not a CONSTANT_NameAndType");
        return null;
    }

    /**
     * Do not allow instantiation of an empty cp_info
     */
    protected cp_info()
    {}

    public boolean validate(cp_info[] constant_pool)
    {
        System.err.println("Attempted to validate base-type constant pool member with tag '" + tag
            + "'.  Constant pool members should always be a derived type.");
        return false;
    }

}

class Utf8_info extends cp_info
{
    @Override
    String getUtf8()
    {
        return new String(info);
    }

    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        return tag == CONSTANT_Utf8;
    }
}
class Integer_info extends cp_info
{
    @Override
    int getInt()
    {
        int value = 0;
        value |= (info[0]<<24);
        value |= (info[1]<<16);
        value |= (info[2]<<8);
        value |= (info[3]);
        return value;
    }

    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        return tag == CONSTANT_Integer;
    }
}
class Float_info extends cp_info
{
    @Override
    float getFloat()
    {
        int value = 0;
        value |= (info[0]<<24);
        value |= (info[1]<<16);
        value |= (info[2]<<8);
        value |= (info[3]);
        return Float.intBitsToFloat(value);
    }

    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        return tag == CONSTANT_Float;
    }
}
class Long_info extends cp_info
{
    @Override
    long getLong()
    {
        long value = 0;
        value |= (info[0]<<24);
        value |= (info[1]<<16);
        value |= (info[2]<<8);
        value |= (info[3]);
        value <<= 32;
        value |= (info[4]<<24);
        value |= (info[5]<<16);
        value |= (info[6]<<8);
        value |= (info[7]);
        return value;
    }

    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        return tag == CONSTANT_Long;
    }
}
class Double_info extends cp_info
{
    @Override
    double getDouble()
    {
        long value = 0;
        value |= (info[0]<<24);
        value |= (info[1]<<16);
        value |= (info[2]<<8);
        value |= (info[3]);
        value <<= 32;
        value |= (info[4]<<24);
        value |= (info[5]<<16);
        value |= (info[6]<<8);
        value |= (info[7]);
        return Double.longBitsToDouble(value);
    }

    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        return tag == CONSTANT_Double;
    }
}
class Class_info extends cp_info
{
    @Override
    class_ref getClassRef()
    {
        class_ref reference = new class_ref();
        reference.name_index = (short)((info[0]<<8) | info[1]);
        return reference;
    }

    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        return tag == CONSTANT_Class;
    }
}
class String_info extends cp_info
{
    @Override
    string_ref getString()
    {
        string_ref reference = new string_ref();
        reference.Utf8_index = (short)((info[0]<<8) | info[1]);
        return reference;
    }

    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        return tag == CONSTANT_String;
    }
}
class Fieldref_info extends cp_info
{
    @Override
    field_ref getField()
    {
        field_ref reference = new field_ref();
        reference.class_index = (short)((info[0]<<8) | info[1]);
        reference.name_and_type_index = (short)((info[2]<<8) | info[3]);
        return reference;
    }
    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        cp_info clss = constant_pool[getInterfaceMethod().class_index];
        cp_info NAT = constant_pool[getInterfaceMethod().name_and_type_index];
        return tag == CONSTANT_NameAndType && clss.tag == CONSTANT_Class && NAT.tag == CONSTANT_NameAndType
                && clss.validate(constant_pool) && NAT.validate(constant_pool);
    }
}
class Methodref_info extends cp_info
{
    @Override
    field_ref getMethod()
    {
        field_ref reference = new field_ref();
        reference.class_index = (short)((info[0]<<8) | info[1]);
        reference.name_and_type_index = (short)((info[2]<<8) | info[3]);
        return reference;
    }
    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        cp_info clss = constant_pool[getInterfaceMethod().class_index];
        cp_info NAT = constant_pool[getInterfaceMethod().name_and_type_index];
        return tag == CONSTANT_NameAndType && clss.tag == CONSTANT_Class && NAT.tag == CONSTANT_NameAndType
                && clss.validate(constant_pool) && NAT.validate(constant_pool);
    }
}
class InterfaceMethodref_info extends cp_info
{
    @Override
    field_ref getInterfaceMethod()
    {
        field_ref reference = new field_ref();
        reference.class_index = (short)((info[0]<<8) | info[1]);
        reference.name_and_type_index = (short)((info[2]<<8) | info[3]);
        return reference;
    }
    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        cp_info clss = constant_pool[getInterfaceMethod().class_index];
        cp_info NAT = constant_pool[getInterfaceMethod().name_and_type_index];
        return tag == CONSTANT_NameAndType && clss.tag == CONSTANT_Class && NAT.tag == CONSTANT_NameAndType
                && clss.validate(constant_pool) && NAT.validate(constant_pool);
    }
}
class NameAndType_info extends cp_info
{
    @Override
    name_and_type_ref getNameAndType()
    {
        name_and_type_ref reference = new name_and_type_ref();
        reference.name_index = (short)((info[0]<<8) | info[1]);
        reference.type_index = (short)((info[2]<<8) | info[3]);
        return reference;
    }

    @Override
    public boolean validate(cp_info[] constant_pool)
    {
        cp_info name = constant_pool[getNameAndType().name_index];
        cp_info type = constant_pool[getNameAndType().type_index];
        return tag == CONSTANT_NameAndType && name.tag == CONSTANT_Utf8 && type.tag == CONSTANT_Utf8
                && name.validate(constant_pool) && type.validate(constant_pool);
    }
}
