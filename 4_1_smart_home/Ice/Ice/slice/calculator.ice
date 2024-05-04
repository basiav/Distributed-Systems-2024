
#ifndef CALC_ICE
#define CALC_ICE

module Demo
{
    enum Color
    {
        Red, Green, Blue, White
    };
    sequence<Color> colors;

    enum InfoKey {
        Location, SmokeLevel, CarbonMonoxideLevel, Volume, Brightness, Color
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
        idempotent Info getInfo();
    };

    exception ValueOutOfRangeException { string reason; };
    interface IBulb extends IDevice{
        void changeColor(Color color);
        void darken() throws ValueOutOfRangeException;
        void lightUp() throws ValueOutOfRangeException;
        void changeBrightness(int percentagePoints) throws ValueOutOfRangeException;
        idempotent colors listColors();
    };

    exception TurnOffSafetyExcpetion { string reason; };
    interface IDeviceNonTurnOff {
        void turnOn();
        void turnOff() throws TurnOffSafetyExcpetion;
        idempotent Info getInfo();
    };

    enum AlarmVolume {
        High, VeryHigh
    };
    interface IDetector extends IDeviceNonTurnOff {
        idempotent string alert();
    };


};

#endif
