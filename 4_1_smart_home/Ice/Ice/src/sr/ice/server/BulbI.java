package sr.ice.server;
import Demo.*;
import com.zeroc.Ice.Current;

import java.util.HashMap;
import java.util.Map;


public class BulbI extends DeviceI implements IBulb {
    Color color;
    int brightnessPercentage;
    String location;
    boolean turnedOn;

    public BulbI(String bathroomBulb) {
        super(bathroomBulb);
//        this.location = bathroomBulb;
        this.color = Color.White;
        this.brightnessPercentage = 10;
    }

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

    @Override
    public Color[] getAllPossibleColors(Current current) {
        return Color.values();
    }

    @Override
    public Info getInfo(Current current) {
        Info info = super.getInfo(current);

        info.moreInfo.put(InfoKey.Brightness, brightnessPercentage + "%");
        info.moreInfo.put(InfoKey.Color, color.name());
        return info;
    }
}