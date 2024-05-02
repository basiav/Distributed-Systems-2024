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
/**
 * Helper class for marshaling/unmarshaling MoreInfo.
 **/

public final class MoreInfoHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, java.util.Map<InfoKey, java.lang.String> v)
    {
        if(v == null)
        {
            ostr.writeSize(0);
        }
        else
        {
            ostr.writeSize(v.size());
            for(java.util.Map.Entry<InfoKey, java.lang.String> e : v.entrySet())
            {
                InfoKey.ice_write(ostr, e.getKey());
                ostr.writeString(e.getValue());
            }
        }
    }

    public static java.util.Map<InfoKey, java.lang.String> read(com.zeroc.Ice.InputStream istr)
    {
        java.util.Map<InfoKey, java.lang.String> v;
        v = new java.util.HashMap<Demo.InfoKey, java.lang.String>();
        int sz0 = istr.readSize();
        for(int i0 = 0; i0 < sz0; i0++)
        {
            InfoKey key;
            key = InfoKey.ice_read(istr);
            String value;
            value = istr.readString();
            v.put(key, value);
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<java.util.Map<InfoKey, java.lang.String>> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Map<InfoKey, java.lang.String> v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            MoreInfoHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<java.util.Map<InfoKey, java.lang.String>> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            java.util.Map<InfoKey, java.lang.String> v;
            v = MoreInfoHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}
