package sr.ice.server;
import Demo.*;
import com.zeroc.Ice.Current;

import java.util.HashMap;
import java.util.Map;


public class BulbI implements Bulb {
    Color color;
    int brightnessPercentage;
    String location;
    boolean turnedOn;

    public BulbI(String bathroomBulb) {
        this.location = bathroomBulb;
        this.color = Color.White;
        this.brightnessPercentage = 10;
    }

//    public BulbI(String location) {
//        this(location, Color.White);
//    }
//
//    public BulbI(String location, Color color) {
//        super(location);
//        this.color = color;
//    }

    @Override
    public void changeColor(Color color, Current current) {
        this.color = color;
        System.out.println("Bulb color changed to " + color + "!");
    }

    @Override
    public void dim(Current current) throws ValueOutOfRangeException {
        changeBrightness(-5, current);
    }

    @Override
    public void brighten(Current current) throws ValueOutOfRangeException {
        changeBrightness(5, current);
    }

    @Override
    public void changeBrightness(int percentagePoints, Current current) throws ValueOutOfRangeException {
        int oldBrightness = brightnessPercentage;
        int newBrightness = brightnessPercentage + percentagePoints;

        if (newBrightness < 0 || newBrightness > 100) throw new ValueOutOfRangeException(String.valueOf(newBrightness));

        brightnessPercentage = newBrightness;
        System.out.println("Bulb brightness changed from " + oldBrightness + " to " + brightnessPercentage);
    }

//    @Override
//    public String[] ice_ids(Current current) {
//        return super.ice_ids(current);
//    }
//
//    @Override
//    public String ice_id(Current current) {
//        return super.ice_id(current);
//    }
//
//    @Override
//    public CompletionStage<OutputStream> _iceDispatch(Incoming in, Current current) throws UserException {
//        return super._iceDispatch(in, current);
//    }


    @Override
    public Color[] getAllPossibleColors(Current current) {
        return Color.values();
    }

//    public Info getInfo(Current current) {
//        return new Info(getStatus(), new HashMap<>(Map.of(InfoKey.Location, location)));
//    }

//    @Override
//    public Info getInfo(Current current) {
////        Info info = super.getInfo(current);
//        Info getInfo = new Info(getStatus(), new HashMap<>(Map.of(InfoKey.Location, location)));
//
//
//        info.moreInfo.put(InfoKey.Brightness, brightnessPercentage + "%");
//        info.moreInfo.put(InfoKey.Color, color.name());
//        return info;
//    }

    protected String getStatus() {
        return turnedOn ? "on" : "off";
    }

    @Override
    public void turnOn(Current current) {
        turnedOn = true;
        changeStatus();
    }

    @Override
    public void turnOff(Current current) {
        turnedOn = false;
        changeStatus();
    }

    private void changeStatus() {
        String status = getStatus();
        System.out.println("Turning " + status + " device...");
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Device turned " + status);
    }
}