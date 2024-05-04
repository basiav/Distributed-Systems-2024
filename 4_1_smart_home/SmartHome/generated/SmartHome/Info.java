//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.10
//
// <auto-generated>
//
// Generated from file `SmartHome.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package SmartHome;

public class Info extends com.zeroc.Ice.Value
{
    public Info()
    {
        this.turnedOn = "";
    }

    public Info(String turnedOn, java.util.Map<InfoKey, java.lang.String> details)
    {
        this.turnedOn = turnedOn;
        this.details = details;
    }

    public String turnedOn;

    public java.util.Map<InfoKey, java.lang.String> details;

    public Info clone()
    {
        return (Info)super.clone();
    }

    public static String ice_staticId()
    {
        return "::SmartHome::Info";
    }

    @Override
    public String ice_id()
    {
        return ice_staticId();
    }

    /** @hidden */
    public static final long serialVersionUID = -885674506L;

    /** @hidden */
    @Override
    protected void _iceWriteImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice(ice_staticId(), -1, true);
        ostr_.writeString(turnedOn);
        AdvancedInfoHelper.write(ostr_, details);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _iceReadImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        turnedOn = istr_.readString();
        details = AdvancedInfoHelper.read(istr_);
        istr_.endSlice();
    }
}