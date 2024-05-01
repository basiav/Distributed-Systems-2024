import grpc
from google.protobuf.message_factory import GetMessageClass
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import (
    ProtoReflectionDescriptorDatabase,
)
import calculator_pb2_grpc
import random

from google._upb._message import (
    MethodDescriptor,
    Descriptor,
    DescriptorPool,
    FieldDescriptor,
)

"""
Compile proto file using
python -m grpc_tools.protoc -I protos --python_out=. --pyi_out=. --grpc_python_out=. protos/calculator.proto
"""


def get_random(descriptor: FieldDescriptor):
    """
    Generates a structure described by the FieldDescriptor.
    Should work for nested types but may fail.
    """
    # generate list if field is repeated
    repeats = 1 if not descriptor.label == FieldDescriptor.LABEL_REPEATED else 5
    ret = []
    while repeats > 0:
        repeats -= 1
        if descriptor.type == FieldDescriptor.TYPE_INT32:
            ret.append(random.randint(1, 10))
        if (
            descriptor.type == FieldDescriptor.TYPE_DOUBLE
            or descriptor.type == FieldDescriptor.TYPE_FLOAT
        ):
            ret.append(round(random.random() * 10, 1))

        # manage nested types
        if descriptor.type == FieldDescriptor.TYPE_MESSAGE:
            # recursively generate them
            nested_objects = {
                field.name: get_random(field)
                for field in descriptor.message_type.fields
            }
            # what the hell is this
            cls = GetMessageClass(descriptor.message_type.fields[0].containing_type)
            # create an object from generated data
            message_object = cls(**nested_objects)
            ret.append(message_object)

        if descriptor.enum_type:
            random_enum_val = random.choice(descriptor.enum_type.values_by_name.keys())
            ret.append(random_enum_val)

    return ret[0] if not descriptor.label == FieldDescriptor.LABEL_REPEATED else ret


def listing():
    services = list_available_services()

    while True:
        try:
            input_ = input(
                "\n\nChoose service number to list available methods or press `X` to exit\n"
            )
            if input_ == "X":
                break
            chosen_service = int(input_)
            list_available_service_methods_with_args(services[chosen_service])
        except IndexError:
            print("Wrong service number")


def list_available_services():
    print("--------------------------")
    print("Available services:\n")
    services = list(
        filter(lambda s: not s.startswith("grpc"), reflection_db.get_services())
    )
    for i, service in enumerate(services):
        name = service.split(".")[-1]
        print(f"({i}) {name}")
    print("---------------------------")

    return services


def list_available_service_methods_with_args(service_name):
    service = desc_pool.FindServiceByName(service_name)
    service_name = service_name.split(".")[-1]
    print("\n_____________________________________________________")
    print(f"Available operations for [{service_name}]:\n")
    for i, method in enumerate(list(map(lambda m: m.full_name, service.methods))):
        method_name = method.split(".")[-1]
        print(f"({i}) {method_name}")

        method_desc = desc_pool.FindMethodByName(method)

        print("\tInput arguments", end=": ")
        for field in method_desc.input_type.fields:
            print(field.full_name.split(".")[-1], end=" ")
        print()

    print("_____________________________________________________")


if __name__ == "__main__":
    # setup connection
    PORT = 50050
    server_address = f"127.0.0.9:{PORT}"
    channel = grpc.insecure_channel(
        server_address, options=(("grpc.enable_http_proxy", 0),)
    )
    basic_stub = calculator_pb2_grpc.CalculatorStub(channel)
    advanced_stub = calculator_pb2_grpc.AdvancedCalculatorStub(channel)

    # setup reflection
    reflection_db = ProtoReflectionDescriptorDatabase(channel)
    desc_pool = DescriptorPool(reflection_db)

    listing()
    print("\n_____________________________________________________")
    print("Generate random requests:\n")

    # EXAMPLE 1 - random service, random choice
    # Random service
    services = list(
        filter(lambda s: not s.startswith("grpc"), reflection_db.get_services())
    )
    service_name = random.choice(services)
    print("Service of choice: ", service_name)
    service_desc = desc_pool.FindServiceByName(service_name)

    # Random method
    print("Available Methods: ", *service_desc.methods_by_name.keys(), sep="\n")
    method_descriptor: MethodDescriptor = random.choice(
        service_desc.methods_by_name.values()
    )
    print("Method of choice: ", method_descriptor.name)

    # Fill-in with random arguments
    input_struct: Descriptor = method_descriptor.input_type
    kwargs = {}
    for field_descriptor in input_struct.fields:
        kwargs[field_descriptor.name] = get_random(field_descriptor)
    print("Arguments: ", kwargs, sep=" ")

    # Create message object
    message_class = GetMessageClass(input_struct)
    class_arguments = message_class(**kwargs)
    stub = basic_stub if service_name == "calculator.Calculator" else advanced_stub
    result = stub.__getattribute__(method_descriptor.name)(class_arguments)
    print("\nResult:\n", result, "\n\n")

    # EXAMPLE 2 - AdvancedCalc with enums
    service_name = r"calculator.AdvancedCalculator"
    print("Service of choice: ", service_name)
    service_desc = desc_pool.FindServiceByName(service_name)
    method_descriptor = service_desc.methods_by_name["ComplexOperation"]
    print("Method of choice: ", method_descriptor.name)
    input_struct = method_descriptor.input_type
    kwargs = {}
    for field_descriptor in input_struct.fields:
        kwargs[field_descriptor.name] = get_random(field_descriptor)
    print("Arguments: ", kwargs, sep=" ")

    message_class = GetMessageClass(input_struct)
    class_arguments = message_class(**kwargs)
    result = advanced_stub.__getattribute__(method_descriptor.name)(class_arguments)
    print("\nResult:\n", result)

    channel.close()

"""
client is able to"
    * connect to the server`
    * learn which server capabilities exists
    * send rpc requests to the enable-reflection server`

--> list all available services on server
    grpcurl -plaintext localhost:50050 list 

--> list all available service methods
    grpcurl -plaintext localhost:50050 list calculator.AdvancedCalculator
    
--> get description of service    
    grpcurl -plaintext localhost:50050 describe calculator.AdvancedCalculator

--> execute method on service running on server
    grpcurl -plaintext -d '{"optype": "SUM", "args": [1,2]}' localhost:50050 calculator.AdvancedCalculator/ComplexOperation

"""
