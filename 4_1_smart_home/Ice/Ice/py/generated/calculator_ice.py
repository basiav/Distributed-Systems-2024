# -*- coding: utf-8 -*-
#
# Copyright (c) ZeroC, Inc. All rights reserved.
#
#
# Ice version 3.7.10
#
# <auto-generated>
#
# Generated from file `calculator.ice'
#
# Warning: do not edit this file.
#
# </auto-generated>
#

from sys import version_info as _version_info_
import Ice, IcePy

# Start of module Demo
_M_Demo = Ice.openModule('Demo')
__name__ = 'Demo'

if 'Color' not in _M_Demo.__dict__:
    _M_Demo.Color = Ice.createTempClass()
    class Color(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    Color.Red = Color("Red", 0)
    Color.Green = Color("Green", 1)
    Color.Blue = Color("Blue", 2)
    Color.White = Color("White", 3)
    Color._enumerators = { 0:Color.Red, 1:Color.Green, 2:Color.Blue, 3:Color.White }

    _M_Demo._t_Color = IcePy.defineEnum('::Demo::Color', Color, (), Color._enumerators)

    _M_Demo.Color = Color
    del Color

<<<<<<< HEAD
if 'InfoKey' not in _M_Demo.__dict__:
    _M_Demo.InfoKey = Ice.createTempClass()
    class InfoKey(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    InfoKey.Location = InfoKey("Location", 0)
    InfoKey.SmokeLevel = InfoKey("SmokeLevel", 1)
    InfoKey.Brightness = InfoKey("Brightness", 2)
    InfoKey.Color = InfoKey("Color", 3)
    InfoKey._enumerators = { 0:InfoKey.Location, 1:InfoKey.SmokeLevel, 2:InfoKey.Brightness, 3:InfoKey.Color }

    _M_Demo._t_InfoKey = IcePy.defineEnum('::Demo::InfoKey', InfoKey, (), InfoKey._enumerators)

    _M_Demo.InfoKey = InfoKey
    del InfoKey

if '_t_MoreInfo' not in _M_Demo.__dict__:
    _M_Demo._t_MoreInfo = IcePy.defineDictionary('::Demo::MoreInfo', (), _M_Demo._t_InfoKey, IcePy._t_string)

if 'Info' not in _M_Demo.__dict__:
    _M_Demo.Info = Ice.createTempClass()
    class Info(Ice.Value):
        def __init__(self, status='', moreInfo=None):
            self.status = status
            self.moreInfo = moreInfo
=======
if 'Device' not in _M_Demo.__dict__:
    _M_Demo.Device = Ice.createTempClass()
    class Device(Ice.Value):
        def __init__(self, location='', turnedOn=False):
            self.location = location
            self.turnedOn = turnedOn
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8

        def ice_id(self):
            return '::Demo::Device'

        @staticmethod
        def ice_staticId():
            return '::Demo::Device'

        def __str__(self):
            return IcePy.stringify(self, _M_Demo._t_Device)

        __repr__ = __str__

<<<<<<< HEAD
    _M_Demo._t_Info = IcePy.defineValue('::Demo::Info', Info, -1, (), False, False, None, (
        ('status', (), IcePy._t_string, False, 0),
        ('moreInfo', (), _M_Demo._t_MoreInfo, False, 0)
    ))
    Info._ice_type = _M_Demo._t_Info
=======
    _M_Demo._t_Device = IcePy.defineValue('::Demo::Device', Device, -1, (), False, False, None, (
        ('location', (), IcePy._t_string, False, 0),
        ('turnedOn', (), IcePy._t_bool, False, 0)
    ))
    Device._ice_type = _M_Demo._t_Device
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8

    _M_Demo.Device = Device
    del Device

<<<<<<< HEAD
if 'Device' not in _M_Demo.__dict__:
    _M_Demo.Device = Ice.createTempClass()
    class Device(Ice.Value):
        def __init__(self, location='', turnedOn=False):
            self.location = location
            self.turnedOn = turnedOn

        def ice_id(self):
            return '::Demo::Device'

        @staticmethod
        def ice_staticId():
            return '::Demo::Device'

        def __str__(self):
            return IcePy.stringify(self, _M_Demo._t_Device)

        __repr__ = __str__

    _M_Demo._t_Device = IcePy.defineValue('::Demo::Device', Device, -1, (), False, False, None, (
        ('location', (), IcePy._t_string, False, 0),
        ('turnedOn', (), IcePy._t_bool, False, 0)
    ))
    Device._ice_type = _M_Demo._t_Device

    _M_Demo.Device = Device
    del Device

_M_Demo._t_IDevice = IcePy.defineValue('::Demo::IDevice', Ice.Value, -1, (), False, True, None, ())

=======
_M_Demo._t_IDevice = IcePy.defineValue('::Demo::IDevice', Ice.Value, -1, (), False, True, None, ())

>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
if 'IDevicePrx' not in _M_Demo.__dict__:
    _M_Demo.IDevicePrx = Ice.createTempClass()
    class IDevicePrx(Ice.ObjectPrx):

        def turnOn(self, context=None):
            return _M_Demo.IDevice._op_turnOn.invoke(self, ((), context))

        def turnOnAsync(self, context=None):
            return _M_Demo.IDevice._op_turnOn.invokeAsync(self, ((), context))

        def begin_turnOn(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IDevice._op_turnOn.begin(self, ((), _response, _ex, _sent, context))

        def end_turnOn(self, _r):
            return _M_Demo.IDevice._op_turnOn.end(self, _r)

        def turnOff(self, context=None):
            return _M_Demo.IDevice._op_turnOff.invoke(self, ((), context))

        def turnOffAsync(self, context=None):
            return _M_Demo.IDevice._op_turnOff.invokeAsync(self, ((), context))

        def begin_turnOff(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IDevice._op_turnOff.begin(self, ((), _response, _ex, _sent, context))

        def end_turnOff(self, _r):
            return _M_Demo.IDevice._op_turnOff.end(self, _r)
<<<<<<< HEAD

        def getInfo(self, context=None):
            return _M_Demo.IDevice._op_getInfo.invoke(self, ((), context))

        def getInfoAsync(self, context=None):
            return _M_Demo.IDevice._op_getInfo.invokeAsync(self, ((), context))

        def begin_getInfo(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IDevice._op_getInfo.begin(self, ((), _response, _ex, _sent, context))

        def end_getInfo(self, _r):
            return _M_Demo.IDevice._op_getInfo.end(self, _r)
=======
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_Demo.IDevicePrx.ice_checkedCast(proxy, '::Demo::IDevice', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_Demo.IDevicePrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::Demo::IDevice'
    _M_Demo._t_IDevicePrx = IcePy.defineProxy('::Demo::IDevice', IDevicePrx)

    _M_Demo.IDevicePrx = IDevicePrx
    del IDevicePrx

    _M_Demo.IDevice = Ice.createTempClass()
    class IDevice(Ice.Object):

        def ice_ids(self, current=None):
            return ('::Demo::IDevice', '::Ice::Object')

        def ice_id(self, current=None):
            return '::Demo::IDevice'

        @staticmethod
        def ice_staticId():
            return '::Demo::IDevice'

        def turnOn(self, current=None):
            raise NotImplementedError("servant method 'turnOn' not implemented")

        def turnOff(self, current=None):
            raise NotImplementedError("servant method 'turnOff' not implemented")

        def getInfo(self, current=None):
            raise NotImplementedError("servant method 'getInfo' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_Demo._t_IDeviceDisp)

        __repr__ = __str__

    _M_Demo._t_IDeviceDisp = IcePy.defineClass('::Demo::IDevice', IDevice, (), None, ())
    IDevice._ice_type = _M_Demo._t_IDeviceDisp

    IDevice._op_turnOn = IcePy.Operation('turnOn', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, ())
    IDevice._op_turnOff = IcePy.Operation('turnOff', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, ())
<<<<<<< HEAD
    IDevice._op_getInfo = IcePy.Operation('getInfo', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_Demo._t_Info, False, 0), ())

    _M_Demo.IDevice = IDevice
    del IDevice

if '_t_colors' not in _M_Demo.__dict__:
    _M_Demo._t_colors = IcePy.defineSequence('::Demo::colors', (), _M_Demo._t_Color)

=======

    _M_Demo.IDevice = IDevice
    del IDevice

if '_t_colors' not in _M_Demo.__dict__:
    _M_Demo._t_colors = IcePy.defineSequence('::Demo::colors', (), _M_Demo._t_Color)

>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
if 'ValueOutOfRangeException' not in _M_Demo.__dict__:
    _M_Demo.ValueOutOfRangeException = Ice.createTempClass()
    class ValueOutOfRangeException(Ice.UserException):
        def __init__(self, reason=''):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::Demo::ValueOutOfRangeException'
<<<<<<< HEAD

    _M_Demo._t_ValueOutOfRangeException = IcePy.defineException('::Demo::ValueOutOfRangeException', ValueOutOfRangeException, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    ValueOutOfRangeException._ice_type = _M_Demo._t_ValueOutOfRangeException

    _M_Demo.ValueOutOfRangeException = ValueOutOfRangeException
    del ValueOutOfRangeException

_M_Demo._t_IBulb = IcePy.defineValue('::Demo::IBulb', Ice.Value, -1, (), False, True, None, ())

if 'IBulbPrx' not in _M_Demo.__dict__:
    _M_Demo.IBulbPrx = Ice.createTempClass()
    class IBulbPrx(_M_Demo.IDevicePrx):

        def changeColor(self, color, context=None):
            return _M_Demo.IBulb._op_changeColor.invoke(self, ((color, ), context))

        def changeColorAsync(self, color, context=None):
            return _M_Demo.IBulb._op_changeColor.invokeAsync(self, ((color, ), context))

        def begin_changeColor(self, color, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IBulb._op_changeColor.begin(self, ((color, ), _response, _ex, _sent, context))

        def end_changeColor(self, _r):
            return _M_Demo.IBulb._op_changeColor.end(self, _r)

        def dim(self, context=None):
            return _M_Demo.IBulb._op_dim.invoke(self, ((), context))

        def dimAsync(self, context=None):
            return _M_Demo.IBulb._op_dim.invokeAsync(self, ((), context))

        def begin_dim(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IBulb._op_dim.begin(self, ((), _response, _ex, _sent, context))

        def end_dim(self, _r):
            return _M_Demo.IBulb._op_dim.end(self, _r)

        def brighten(self, context=None):
            return _M_Demo.IBulb._op_brighten.invoke(self, ((), context))

        def brightenAsync(self, context=None):
            return _M_Demo.IBulb._op_brighten.invokeAsync(self, ((), context))

        def begin_brighten(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IBulb._op_brighten.begin(self, ((), _response, _ex, _sent, context))

        def end_brighten(self, _r):
            return _M_Demo.IBulb._op_brighten.end(self, _r)

        def changeBrightness(self, percentagePoints, context=None):
            return _M_Demo.IBulb._op_changeBrightness.invoke(self, ((percentagePoints, ), context))

        def changeBrightnessAsync(self, percentagePoints, context=None):
            return _M_Demo.IBulb._op_changeBrightness.invokeAsync(self, ((percentagePoints, ), context))

        def begin_changeBrightness(self, percentagePoints, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IBulb._op_changeBrightness.begin(self, ((percentagePoints, ), _response, _ex, _sent, context))

        def end_changeBrightness(self, _r):
            return _M_Demo.IBulb._op_changeBrightness.end(self, _r)

        def getAllPossibleColors(self, context=None):
            return _M_Demo.IBulb._op_getAllPossibleColors.invoke(self, ((), context))

        def getAllPossibleColorsAsync(self, context=None):
            return _M_Demo.IBulb._op_getAllPossibleColors.invokeAsync(self, ((), context))

=======

    _M_Demo._t_ValueOutOfRangeException = IcePy.defineException('::Demo::ValueOutOfRangeException', ValueOutOfRangeException, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    ValueOutOfRangeException._ice_type = _M_Demo._t_ValueOutOfRangeException

    _M_Demo.ValueOutOfRangeException = ValueOutOfRangeException
    del ValueOutOfRangeException

_M_Demo._t_IBulb = IcePy.defineValue('::Demo::IBulb', Ice.Value, -1, (), False, True, None, ())

if 'IBulbPrx' not in _M_Demo.__dict__:
    _M_Demo.IBulbPrx = Ice.createTempClass()
    class IBulbPrx(_M_Demo.IDevicePrx):

        def changeColor(self, color, context=None):
            return _M_Demo.IBulb._op_changeColor.invoke(self, ((color, ), context))

        def changeColorAsync(self, color, context=None):
            return _M_Demo.IBulb._op_changeColor.invokeAsync(self, ((color, ), context))

        def begin_changeColor(self, color, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IBulb._op_changeColor.begin(self, ((color, ), _response, _ex, _sent, context))

        def end_changeColor(self, _r):
            return _M_Demo.IBulb._op_changeColor.end(self, _r)

        def dim(self, context=None):
            return _M_Demo.IBulb._op_dim.invoke(self, ((), context))

        def dimAsync(self, context=None):
            return _M_Demo.IBulb._op_dim.invokeAsync(self, ((), context))

        def begin_dim(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IBulb._op_dim.begin(self, ((), _response, _ex, _sent, context))

        def end_dim(self, _r):
            return _M_Demo.IBulb._op_dim.end(self, _r)

        def brighten(self, context=None):
            return _M_Demo.IBulb._op_brighten.invoke(self, ((), context))

        def brightenAsync(self, context=None):
            return _M_Demo.IBulb._op_brighten.invokeAsync(self, ((), context))

        def begin_brighten(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IBulb._op_brighten.begin(self, ((), _response, _ex, _sent, context))

        def end_brighten(self, _r):
            return _M_Demo.IBulb._op_brighten.end(self, _r)

        def changeBrightness(self, percentagePoints, context=None):
            return _M_Demo.IBulb._op_changeBrightness.invoke(self, ((percentagePoints, ), context))

        def changeBrightnessAsync(self, percentagePoints, context=None):
            return _M_Demo.IBulb._op_changeBrightness.invokeAsync(self, ((percentagePoints, ), context))

        def begin_changeBrightness(self, percentagePoints, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IBulb._op_changeBrightness.begin(self, ((percentagePoints, ), _response, _ex, _sent, context))

        def end_changeBrightness(self, _r):
            return _M_Demo.IBulb._op_changeBrightness.end(self, _r)

        def getAllPossibleColors(self, context=None):
            return _M_Demo.IBulb._op_getAllPossibleColors.invoke(self, ((), context))

        def getAllPossibleColorsAsync(self, context=None):
            return _M_Demo.IBulb._op_getAllPossibleColors.invokeAsync(self, ((), context))

>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
        def begin_getAllPossibleColors(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IBulb._op_getAllPossibleColors.begin(self, ((), _response, _ex, _sent, context))

        def end_getAllPossibleColors(self, _r):
            return _M_Demo.IBulb._op_getAllPossibleColors.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_Demo.IBulbPrx.ice_checkedCast(proxy, '::Demo::IBulb', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_Demo.IBulbPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::Demo::IBulb'
    _M_Demo._t_IBulbPrx = IcePy.defineProxy('::Demo::IBulb', IBulbPrx)

    _M_Demo.IBulbPrx = IBulbPrx
    del IBulbPrx

    _M_Demo.IBulb = Ice.createTempClass()
    class IBulb(_M_Demo.IDevice):

        def ice_ids(self, current=None):
            return ('::Demo::IBulb', '::Demo::IDevice', '::Ice::Object')

        def ice_id(self, current=None):
            return '::Demo::IBulb'

        @staticmethod
        def ice_staticId():
            return '::Demo::IBulb'

        def changeColor(self, color, current=None):
            raise NotImplementedError("servant method 'changeColor' not implemented")
<<<<<<< HEAD

        def dim(self, current=None):
            raise NotImplementedError("servant method 'dim' not implemented")

        def brighten(self, current=None):
            raise NotImplementedError("servant method 'brighten' not implemented")

        def changeBrightness(self, percentagePoints, current=None):
            raise NotImplementedError("servant method 'changeBrightness' not implemented")

        def getAllPossibleColors(self, current=None):
            raise NotImplementedError("servant method 'getAllPossibleColors' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_Demo._t_IBulbDisp)
=======

        def dim(self, current=None):
            raise NotImplementedError("servant method 'dim' not implemented")

        def brighten(self, current=None):
            raise NotImplementedError("servant method 'brighten' not implemented")
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8

        def changeBrightness(self, percentagePoints, current=None):
            raise NotImplementedError("servant method 'changeBrightness' not implemented")

<<<<<<< HEAD
    _M_Demo._t_IBulbDisp = IcePy.defineClass('::Demo::IBulb', IBulb, (), None, (_M_Demo._t_IDeviceDisp,))
    IBulb._ice_type = _M_Demo._t_IBulbDisp

    IBulb._op_changeColor = IcePy.Operation('changeColor', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_Demo._t_Color, False, 0),), (), None, ())
    IBulb._op_dim = IcePy.Operation('dim', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, (_M_Demo._t_ValueOutOfRangeException,))
    IBulb._op_brighten = IcePy.Operation('brighten', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, (_M_Demo._t_ValueOutOfRangeException,))
    IBulb._op_changeBrightness = IcePy.Operation('changeBrightness', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_int, False, 0),), (), None, (_M_Demo._t_ValueOutOfRangeException,))
    IBulb._op_getAllPossibleColors = IcePy.Operation('getAllPossibleColors', Ice.OperationMode.Idempotent, Ice.OperationMode.Idempotent, False, None, (), (), (), ((), _M_Demo._t_colors, False, 0), ())

    _M_Demo.IBulb = IBulb
    del IBulb

if 'TurnOffSafetyExcpetion' not in _M_Demo.__dict__:
    _M_Demo.TurnOffSafetyExcpetion = Ice.createTempClass()
    class TurnOffSafetyExcpetion(Ice.UserException):
        def __init__(self, reason=''):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::Demo::TurnOffSafetyExcpetion'

    _M_Demo._t_TurnOffSafetyExcpetion = IcePy.defineException('::Demo::TurnOffSafetyExcpetion', TurnOffSafetyExcpetion, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    TurnOffSafetyExcpetion._ice_type = _M_Demo._t_TurnOffSafetyExcpetion

    _M_Demo.TurnOffSafetyExcpetion = TurnOffSafetyExcpetion
    del TurnOffSafetyExcpetion

_M_Demo._t_IDeviceNonTurnOff = IcePy.defineValue('::Demo::IDeviceNonTurnOff', Ice.Value, -1, (), False, True, None, ())

if 'IDeviceNonTurnOffPrx' not in _M_Demo.__dict__:
    _M_Demo.IDeviceNonTurnOffPrx = Ice.createTempClass()
    class IDeviceNonTurnOffPrx(Ice.ObjectPrx):

        def turnOn(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOn.invoke(self, ((), context))

        def turnOnAsync(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOn.invokeAsync(self, ((), context))

        def begin_turnOn(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOn.begin(self, ((), _response, _ex, _sent, context))

        def end_turnOn(self, _r):
            return _M_Demo.IDeviceNonTurnOff._op_turnOn.end(self, _r)

        def turnOff(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOff.invoke(self, ((), context))

        def turnOffAsync(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOff.invokeAsync(self, ((), context))

        def begin_turnOff(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOff.begin(self, ((), _response, _ex, _sent, context))

        def end_turnOff(self, _r):
            return _M_Demo.IDeviceNonTurnOff._op_turnOff.end(self, _r)

        def getInfo(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_getInfo.invoke(self, ((), context))

        def getInfoAsync(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_getInfo.invokeAsync(self, ((), context))

        def begin_getInfo(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_getInfo.begin(self, ((), _response, _ex, _sent, context))

        def end_getInfo(self, _r):
            return _M_Demo.IDeviceNonTurnOff._op_getInfo.end(self, _r)
=======
        def getAllPossibleColors(self, current=None):
            raise NotImplementedError("servant method 'getAllPossibleColors' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_Demo._t_IBulbDisp)

        __repr__ = __str__

    _M_Demo._t_IBulbDisp = IcePy.defineClass('::Demo::IBulb', IBulb, (), None, (_M_Demo._t_IDeviceDisp,))
    IBulb._ice_type = _M_Demo._t_IBulbDisp

    IBulb._op_changeColor = IcePy.Operation('changeColor', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_Demo._t_Color, False, 0),), (), None, ())
    IBulb._op_dim = IcePy.Operation('dim', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, (_M_Demo._t_ValueOutOfRangeException,))
    IBulb._op_brighten = IcePy.Operation('brighten', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, (_M_Demo._t_ValueOutOfRangeException,))
    IBulb._op_changeBrightness = IcePy.Operation('changeBrightness', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_int, False, 0),), (), None, (_M_Demo._t_ValueOutOfRangeException,))
    IBulb._op_getAllPossibleColors = IcePy.Operation('getAllPossibleColors', Ice.OperationMode.Idempotent, Ice.OperationMode.Idempotent, False, None, (), (), (), ((), _M_Demo._t_colors, False, 0), ())

    _M_Demo.IBulb = IBulb
    del IBulb

if 'TurnOffSafetyExcpetion' not in _M_Demo.__dict__:
    _M_Demo.TurnOffSafetyExcpetion = Ice.createTempClass()
    class TurnOffSafetyExcpetion(Ice.UserException):
        def __init__(self, reason=''):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::Demo::TurnOffSafetyExcpetion'

    _M_Demo._t_TurnOffSafetyExcpetion = IcePy.defineException('::Demo::TurnOffSafetyExcpetion', TurnOffSafetyExcpetion, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    TurnOffSafetyExcpetion._ice_type = _M_Demo._t_TurnOffSafetyExcpetion

    _M_Demo.TurnOffSafetyExcpetion = TurnOffSafetyExcpetion
    del TurnOffSafetyExcpetion

_M_Demo._t_IDeviceNonTurnOff = IcePy.defineValue('::Demo::IDeviceNonTurnOff', Ice.Value, -1, (), False, True, None, ())

if 'IDeviceNonTurnOffPrx' not in _M_Demo.__dict__:
    _M_Demo.IDeviceNonTurnOffPrx = Ice.createTempClass()
    class IDeviceNonTurnOffPrx(Ice.ObjectPrx):

        def turnOn(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOn.invoke(self, ((), context))

        def turnOnAsync(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOn.invokeAsync(self, ((), context))

        def begin_turnOn(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOn.begin(self, ((), _response, _ex, _sent, context))

        def end_turnOn(self, _r):
            return _M_Demo.IDeviceNonTurnOff._op_turnOn.end(self, _r)

        def turnOff(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOff.invoke(self, ((), context))

        def turnOffAsync(self, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOff.invokeAsync(self, ((), context))

        def begin_turnOff(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IDeviceNonTurnOff._op_turnOff.begin(self, ((), _response, _ex, _sent, context))

        def end_turnOff(self, _r):
            return _M_Demo.IDeviceNonTurnOff._op_turnOff.end(self, _r)
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_Demo.IDeviceNonTurnOffPrx.ice_checkedCast(proxy, '::Demo::IDeviceNonTurnOff', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_Demo.IDeviceNonTurnOffPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::Demo::IDeviceNonTurnOff'
    _M_Demo._t_IDeviceNonTurnOffPrx = IcePy.defineProxy('::Demo::IDeviceNonTurnOff', IDeviceNonTurnOffPrx)

    _M_Demo.IDeviceNonTurnOffPrx = IDeviceNonTurnOffPrx
    del IDeviceNonTurnOffPrx

    _M_Demo.IDeviceNonTurnOff = Ice.createTempClass()
    class IDeviceNonTurnOff(Ice.Object):

        def ice_ids(self, current=None):
            return ('::Demo::IDeviceNonTurnOff', '::Ice::Object')

        def ice_id(self, current=None):
            return '::Demo::IDeviceNonTurnOff'

        @staticmethod
        def ice_staticId():
            return '::Demo::IDeviceNonTurnOff'

        def turnOn(self, current=None):
            raise NotImplementedError("servant method 'turnOn' not implemented")

        def turnOff(self, current=None):
            raise NotImplementedError("servant method 'turnOff' not implemented")

<<<<<<< HEAD
        def getInfo(self, current=None):
            raise NotImplementedError("servant method 'getInfo' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_Demo._t_IDeviceNonTurnOffDisp)

=======
        def __str__(self):
            return IcePy.stringify(self, _M_Demo._t_IDeviceNonTurnOffDisp)

>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
        __repr__ = __str__

    _M_Demo._t_IDeviceNonTurnOffDisp = IcePy.defineClass('::Demo::IDeviceNonTurnOff', IDeviceNonTurnOff, (), None, ())
    IDeviceNonTurnOff._ice_type = _M_Demo._t_IDeviceNonTurnOffDisp

    IDeviceNonTurnOff._op_turnOn = IcePy.Operation('turnOn', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, ())
    IDeviceNonTurnOff._op_turnOff = IcePy.Operation('turnOff', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, (_M_Demo._t_TurnOffSafetyExcpetion,))
<<<<<<< HEAD
    IDeviceNonTurnOff._op_getInfo = IcePy.Operation('getInfo', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_Demo._t_Info, False, 0), ())
=======
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8

    _M_Demo.IDeviceNonTurnOff = IDeviceNonTurnOff
    del IDeviceNonTurnOff

_M_Demo._t_IDetector = IcePy.defineValue('::Demo::IDetector', Ice.Value, -1, (), False, True, None, ())

if 'IDetectorPrx' not in _M_Demo.__dict__:
    _M_Demo.IDetectorPrx = Ice.createTempClass()
    class IDetectorPrx(_M_Demo.IDeviceNonTurnOffPrx):

        def alert(self, context=None):
            return _M_Demo.IDetector._op_alert.invoke(self, ((), context))

        def alertAsync(self, context=None):
            return _M_Demo.IDetector._op_alert.invokeAsync(self, ((), context))

        def begin_alert(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Demo.IDetector._op_alert.begin(self, ((), _response, _ex, _sent, context))

        def end_alert(self, _r):
            return _M_Demo.IDetector._op_alert.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_Demo.IDetectorPrx.ice_checkedCast(proxy, '::Demo::IDetector', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_Demo.IDetectorPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::Demo::IDetector'
    _M_Demo._t_IDetectorPrx = IcePy.defineProxy('::Demo::IDetector', IDetectorPrx)

    _M_Demo.IDetectorPrx = IDetectorPrx
    del IDetectorPrx

    _M_Demo.IDetector = Ice.createTempClass()
    class IDetector(_M_Demo.IDeviceNonTurnOff):

        def ice_ids(self, current=None):
            return ('::Demo::IDetector', '::Demo::IDeviceNonTurnOff', '::Ice::Object')

        def ice_id(self, current=None):
            return '::Demo::IDetector'

        @staticmethod
        def ice_staticId():
            return '::Demo::IDetector'

        def alert(self, current=None):
            raise NotImplementedError("servant method 'alert' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_Demo._t_IDetectorDisp)

        __repr__ = __str__

    _M_Demo._t_IDetectorDisp = IcePy.defineClass('::Demo::IDetector', IDetector, (), None, (_M_Demo._t_IDeviceNonTurnOffDisp,))
    IDetector._ice_type = _M_Demo._t_IDetectorDisp

    IDetector._op_alert = IcePy.Operation('alert', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), IcePy._t_string, False, 0), ())

    _M_Demo.IDetector = IDetector
    del IDetector

# End of module Demo
