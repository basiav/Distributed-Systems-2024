import grpc
from google.protobuf.message_factory import GetMessageClass
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import (
    ProtoReflectionDescriptorDatabase,
)
import calculator_pb2_grpc
import grpc_requests

from google._upb._message import (
    DescriptorPool,
)

"""
Compile proto file using
python -m grpc_tools.protoc -I protos --python_out=. --pyi_out=. --grpc_python_out=. protos/calculator.proto
"""


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


def send_example_requests():
    print("\tExample rpc requests to the enable-reflection server")

    print("EXAMPLE 1 - SimpleAdd")
    example1()

    print("EXAMPLE 2")
    example2()

    print("EXAMPLE 3")
    example3()

    print("EXAMPLE 4")
    example4()


# EXAMPLE 1 - SimpleAdd with grpc_requests
def example1():
    arg1, arg2 = 10, 12
    print("Args: ", arg1, arg2)
    result = client.request(
        "calculator.Calculator", "SimpleAdd", {"arg1": arg1, "arg2": arg2}
    )
    print(f"SimpleAdd result: {result}\n")


# EXAMPLE 2 - AdvancedCalc with enums, invocation on stub
def example2():
    service_name = "calculator.AdvancedCalculator"
    print("Service of choice: ", service_name)
    service_desc = desc_pool.FindServiceByName(service_name)
    method_descriptor = service_desc.methods_by_name["ComplexOperation"]
    print("Method of choice: ", method_descriptor.name)
    input_struct = method_descriptor.input_type
    # {'optype': 'SUM', 'args': [4.3, 2.4, 2.1, 5.9, 4.9]}
    kwargs = {}
    kwargs["optype"] = "SUM"
    kwargs["args"] = [4.3, 2.4, 2.1, 5.9, 4.9]
    print("Args: ", kwargs, sep=" ")

    message_class = GetMessageClass(input_struct)
    class_arguments = message_class(**kwargs)
    result = advanced_stub.__getattribute__(method_descriptor.name)(class_arguments)
    print("Result:", result)


# EXAMPLE 3 - AdvancedCalc with enums, invocation on stub
def example3():
    service_name = "calculator.AdvancedCalculator"
    print("Service of choice: ", service_name)
    service_desc = desc_pool.FindServiceByName(service_name)
    method_descriptor = service_desc.methods_by_name["ComplexOperation"]
    print("Method of choice: ", method_descriptor.name)
    input_struct = method_descriptor.input_type
    # {'optype': 'MAX', 'args': [28.3, 2.4, 11, 68.5, 4.9]}
    kwargs = {}
    kwargs["optype"] = "MAX"
    kwargs["args"] = [28.3, 2.4, 11, 68.5, 4.9]
    print("Args: ", kwargs, sep=" ")

    message_class = GetMessageClass(input_struct)
    class_arguments = message_class(**kwargs)
    result = advanced_stub.__getattribute__(method_descriptor.name)(class_arguments)
    print("Result:", result)


# EXAMPLE 4 - AdvancedCalc with enums with grpc_requests
def example4():
    args = [1.88, 6.1, 35.1, 34.4, 2, 11]
    operations = ["SUM", "AVG", "MIN", "MAX"]

    print(f"args: {args}")
    for op in operations:
        result = client.request(
            "calculator.AdvancedCalculator",
            "ComplexOperation",
            {"optype": op, "args": args},
        )
        print(f"Operation {op}: {result}")


if __name__ == "__main__":
    # setup connection
    PORT = 50050
    server_address = f"127.0.0.9:{PORT}"
    channel = grpc.insecure_channel(
        server_address, options=(("grpc.enable_http_proxy", 0),)
    )
    basic_stub = calculator_pb2_grpc.CalculatorStub(channel)
    advanced_stub = calculator_pb2_grpc.AdvancedCalculatorStub(channel)
    client = grpc_requests.Client.get_by_endpoint(server_address)

    # setup reflection
    reflection_db = ProtoReflectionDescriptorDatabase(channel)
    desc_pool = DescriptorPool(reflection_db)

    listing()
    print("\n_____________________________________________________")

    send_example_requests()

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
