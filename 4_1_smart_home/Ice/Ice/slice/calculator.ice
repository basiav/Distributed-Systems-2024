
#ifndef CALC_ICE
#define CALC_ICE

module Demo
{
    enum Color
    {
        Red, Green, Blue, White
    };

    enum InfoKey {
        Location, SmokeLevel, Brightness, Color
    };
    dictionary<InfoKey, string> MoreInfo;

    class Info {
        string status;
        MoreInfo moreInfo;
    };

    interface IDevice {
        void turnOn();
        void turnOff();
        Info getInfo();
    };

    class Device {
        string location;
        bool turnedOn;
    };

    sequence<Color> colors;

    exception ValueOutOfRangeException { string reason; };

    interface IBulb extends IDevice{
        void changeColor(Color color);
        void dim() throws ValueOutOfRangeException;
        void brighten() throws ValueOutOfRangeException;
        void changeBrightness(int percentagePoints) throws ValueOutOfRangeException;
        idempotent colors getAllPossibleColors();
    };

    exception TurnOffSafetyExcpetion { string reason; };
    interface IDeviceNonTurnOff {
        void turnOn();
        void turnOff() throws TurnOffSafetyExcpetion;
        Info getInfo();
    };

    interface IDetector extends IDeviceNonTurnOff {
        string alert();
    };


};

#endif
