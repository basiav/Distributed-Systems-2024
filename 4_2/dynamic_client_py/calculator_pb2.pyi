from google.protobuf.internal import containers as _containers
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Iterable as _Iterable, Mapping as _Mapping, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class ArithmeticOpArguments(_message.Message):
    __slots__ = ("arg1", "arg2")
    ARG1_FIELD_NUMBER: _ClassVar[int]
    ARG2_FIELD_NUMBER: _ClassVar[int]
    arg1: int
    arg2: int
    def __init__(self, arg1: _Optional[int] = ..., arg2: _Optional[int] = ...) -> None: ...

class SingleIntResult(_message.Message):
    __slots__ = ("res",)
    RES_FIELD_NUMBER: _ClassVar[int]
    res: int
    def __init__(self, res: _Optional[int] = ...) -> None: ...

class ListArithmeticOpArguments(_message.Message):
    __slots__ = ("args",)
    ARGS_FIELD_NUMBER: _ClassVar[int]
    args: _containers.RepeatedScalarFieldContainer[int]
    def __init__(self, args: _Optional[_Iterable[int]] = ...) -> None: ...

class Complex(_message.Message):
    __slots__ = ("real", "imag")
    REAL_FIELD_NUMBER: _ClassVar[int]
    IMAG_FIELD_NUMBER: _ClassVar[int]
    real: float
    imag: float
    def __init__(self, real: _Optional[float] = ..., imag: _Optional[float] = ...) -> None: ...

class ListComplexOpArguments(_message.Message):
    __slots__ = ("args",)
    ARGS_FIELD_NUMBER: _ClassVar[int]
    args: _containers.RepeatedCompositeFieldContainer[Complex]
    def __init__(self, args: _Optional[_Iterable[_Union[Complex, _Mapping]]] = ...) -> None: ...
