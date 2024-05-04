package sr.ice.server;
import Demo.*;
import com.zeroc.Ice.Current;


public class BulbI extends DeviceI implements IBulb {
    Color color = Color.White;
    int brightnessPercentage = 10;

    public BulbI(String location) {
        super(location);
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
    public void darken(Current current) throws ValueOutOfRangeException {
        changeBrightness(-5, current);
    }

    @Override
    public void lightUp(Current current) throws ValueOutOfRangeException {
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
    public Color[] listColors(Current current) {
        return Color.values();
    }

    @Override
    public Info getInfo(Current current) {
        Info info = super.getInfo(current);
        info.details.put(InfoKey.Brightness, brightnessPercentage + "%");
        info.details.put(InfoKey.Color, color.name());
        return info;
    }
}