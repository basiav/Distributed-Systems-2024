
#ifndef CALC_ICE
#define CALC_ICE

module Demo
{
    enum Color
    {
        Red, Green, Blue, White
    };

<<<<<<< HEAD
    enum InfoKey {
        Location, SmokeLevel, Brightness, Color
    };
    dictionary<InfoKey, string> MoreInfo;

    class Info {
        string status;
        MoreInfo moreInfo;
    };

    class Device {
        string location;
        bool turnedOn;
    };

    interface IDevice {
        void turnOn();
        void turnOff();
        Info getInfo();
    };

=======
//    enum InfoKey {
//        Temperature, SmokeLevel, Location, Brightness, Color
//    };
////    dictionary<string, string> dict;
//
//    class Info {
//        int status;
////        dictionary<InfoKey, string> moreInfo;
//    };

    class Device {
        string location;
        bool turnedOn;
    };

    interface IDevice {
        void turnOn();
        void turnOff();
    };

>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
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
<<<<<<< HEAD
        Info getInfo();
=======
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
    };

    interface IDetector extends IDeviceNonTurnOff {
        string alert();
    };


};

#endif
