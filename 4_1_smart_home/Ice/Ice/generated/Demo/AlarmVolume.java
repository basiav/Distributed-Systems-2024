//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.10
//
// <auto-generated>
//
// Generated from file `calculator.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Demo;

public enum AlarmVolume implements java.io.Serializable
{
    High(0),
    VeryHigh(1);

    public int value()
    {
        return _value;
    }

    public static AlarmVolume valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return High;
        case 1:
            return VeryHigh;
        }
        return null;
    }

    private AlarmVolume(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 1);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, AlarmVolume v)
    {
        if(v == null)
        {
            ostr.writeEnum(Demo.AlarmVolume.High.value(), 1);
        }
        else
        {
            ostr.writeEnum(v.value(), 1);
        }
    }

    public static AlarmVolume ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(1);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<AlarmVolume> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, AlarmVolume v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<AlarmVolume> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            return java.util.Optional.of(ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static AlarmVolume validate(int v)
    {
        final AlarmVolume e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
