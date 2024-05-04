
#ifndef CALC_ICE
#define CALC_ICE

module Demo
{
    enum Color
    {
        Red, Green, Blue, White
    };

    enum InfoKey {
        Location, SmokeLevel, CarbonMonoxideLevel, Brightness, Color
    };
    dictionary<InfoKey, string> AdvancedInfo;

    class Info {
        string turnedOn;
        AdvancedInfo details;
    };

    exception WrongMethodException { string reason; };
    interface IDevice {
        void turnOn() throws WrongMethodException;
        void turnOff() throws WrongMethodException;
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

    enum AlarmVolume {
        High, VeryHigh
    };
    interface IDetector extends IDeviceNonTurnOff {
        string alert();
    };


};

#endif
