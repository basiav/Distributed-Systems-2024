
#ifndef CALC_ICE
#define CALC_ICE

module Demo
{
    enum Color
    {
        Red, Green, Blue, White
    };

    enum InfoKey {
        Temperature, SmokeLevel, Location, Brightness, Color
    };
//    dictionary<string, string> dict;

    class Info {
        int status;
//        dictionary<InfoKey, string> moreInfo;
    };

//    class Device {
//        bool turnedOn;
//        string location;
//
//        void turnOn();
//        void turnOff();
//    };
    interface Device {
        void turnOn();
        void turnOff();
    };

    class Printer {
        void print(string message);
    };

//    class Bulb {
//        Color color;
//        int brightnessPercentage;
//
//        void changeColor(Color color);
//        void dim();
//        void brighten();
//    };
    sequence<Color> colors;

    exception ValueOutOfRangeException { string reason; };

    interface Bulb extends Device{
        void changeColor(Color color);
        void dim() throws ValueOutOfRangeException;
        void brighten() throws ValueOutOfRangeException;
        void changeBrightness(int percentagePoints) throws ValueOutOfRangeException;
        idempotent colors getAllPossibleColors();
    };



};

#endif
