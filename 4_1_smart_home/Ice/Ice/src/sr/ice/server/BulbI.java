package sr.ice.server;
import Demo.*;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.OutputStream;
import com.zeroc.Ice.UserException;
import com.zeroc.IceInternal.Incoming;

import java.util.concurrent.CompletionStage;


public class BulbI extends DeviceI implements Bulb {
    Color color;
    int brightnessPercentage;

    public BulbI(String location) {
        this(location, Color.White);
    }

    public BulbI(String location, Color color) {
        super(location);
        this.color = color;
    }

    @Override
    public void changeColor(Color color, Current current) {
        this.color = color;
        System.out.println("Bulb color changed to " + color + "!");
    }

    @Override
    public void dim(Current current) {
        changeBrightness(-5, current);
    }

    @Override
    public void brighten(Current current){
        changeBrightness(5, current);
    }

    @Override
    public void changeBrightness(int percentagePoints, Current current){
        int oldBrightness = brightnessPercentage;
        int newBrightness = brightnessPercentage + percentagePoints;

        brightnessPercentage = newBrightness;
        System.out.println("Bulb brightness changed from " + oldBrightness + " to " + brightnessPercentage);
    }

    @Override
    public String[] ice_ids(Current current) {
        return super.ice_ids(current);
    }

    @Override
    public String ice_id(Current current) {
        return super.ice_id(current);
    }

    @Override
    public CompletionStage<OutputStream> _iceDispatch(Incoming in, Current current) throws UserException {
        return super._iceDispatch(in, current);
    }


//    @Override
//    public Color[] getAllPossibleColors(Current current) {
//        return Color.values();
//    }
//
//    @Override
//    public Info getInfo(Current current) {
//        Info info = super.getInfo(current);
//
//        info.moreInfo.put(InfoKey.Brightness, brightnessPercentage + "%");
//        info.moreInfo.put(InfoKey.Color, color.name());
//        return info;
//    }
}