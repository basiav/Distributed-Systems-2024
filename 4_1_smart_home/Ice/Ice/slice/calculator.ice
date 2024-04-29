
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

    class Info {
        int status;
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
    interface Bulb {
        void changeColor(Color color);
        void dim();
        void brighten();
        void changeBrightness(int percentagePoints);
    };



};

#endif
