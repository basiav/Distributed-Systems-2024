from google.protobuf.internal import containers as _containers
from google.protobuf.internal import enum_type_wrapper as _enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Iterable as _Iterable, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class OperationType(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
    __slots__ = ()
    SUM: _ClassVar[OperationType]
    AVG: _ClassVar[OperationType]
    MIN: _ClassVar[OperationType]
    MAX: _ClassVar[OperationType]
SUM: OperationType
AVG: OperationType
MIN: OperationType
MAX: OperationType

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

class ComplexArithmeticOpArguments(_message.Message):
    __slots__ = ("optype", "args")
    OPTYPE_FIELD_NUMBER: _ClassVar[int]
    ARGS_FIELD_NUMBER: _ClassVar[int]
    optype: OperationType
    args: _containers.RepeatedScalarFieldContainer[float]
    def __init__(self, optype: _Optional[_Union[OperationType, str]] = ..., args: _Optional[_Iterable[float]] = ...) -> None: ...

class ComplexArithmeticOpResult(_message.Message):
    __slots__ = ("res",)
    RES_FIELD_NUMBER: _ClassVar[int]
    res: float
    def __init__(self, res: _Optional[float] = ...) -> None: ...

class ListArithmeticOpArguments(_message.Message):
    __slots__ = ("args",)
    ARGS_FIELD_NUMBER: _ClassVar[int]
    args: _containers.RepeatedScalarFieldContainer[int]
    def __init__(self, args: _Optional[_Iterable[int]] = ...) -> None: ...
