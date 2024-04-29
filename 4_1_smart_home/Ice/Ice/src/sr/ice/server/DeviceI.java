package sr.ice.server;

//package server;

import Demo.*;
import com.zeroc.Ice.Current;

import java.util.HashMap;
import java.util.Map;

public abstract class DeviceI implements Device {
    boolean turnedOn;
    String location;

    public DeviceI(String location) {
        this.location = location;
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

//    @Override
//    public Info getInfo(Current current) {
//        return new Info(getStatus(), new HashMap<>(Map.of(InfoKey.Location, location)));
//    }

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

    protected String getStatus() {
        return turnedOn ? "on" : "off";
    }

}