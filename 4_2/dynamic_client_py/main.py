import grpc
from google.protobuf.message_factory import GetMessageClass
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import ProtoReflectionDescriptorDatabase
import calculator_pb2_grpc
import random

# not necessary, but helpful for type hints
from google._upb._message import MethodDescriptor, Descriptor, DescriptorPool, FieldDescriptor

"""
Compile proto file using
python -m grpc_tools.protoc -I protos --python_out=. --pyi_out=. --grpc_python_out=. protos/calculator.proto
"""


def get_random(descriptor: FieldDescriptor):
    """
    Generates a structure described by the FieldDescriptor.
    Should mork for nested types but may fail.
    """
    # generate list if field is repeated
    repeats = 1 if not descriptor.label == FieldDescriptor.LABEL_REPEATED else 5
    ret = []
    while repeats > 0:
        repeats -= 1
        if descriptor.type == FieldDescriptor.TYPE_INT32:
            ret.append(random.randint(1, 10))
        if descriptor.type == FieldDescriptor.TYPE_DOUBLE or descriptor.type == FieldDescriptor.TYPE_FLOAT:
            ret.append(round(random.random() * 10, 1))

        # manage nested types
        if descriptor.type == FieldDescriptor.TYPE_MESSAGE:
            # recursively generate them
            nested_objects = {field.name: get_random(field) for field in descriptor.message_type.fields}
            # what the hell is this
            cls = GetMessageClass(descriptor.message_type.fields[0].containing_type)
            # create an object from generated data
            message_object = cls(**nested_objects)
            ret.append(message_object)

    return ret[0] if not descriptor.label == FieldDescriptor.LABEL_REPEATED else ret


# set up connection
channel = grpc.insecure_channel("127.0.0.9:50051", options=(('grpc.enable_http_proxy', 0),))
basic_stub = calculator_pb2_grpc.CalculatorStub(channel)
advanced_stub = calculator_pb2_grpc.AdvancedCalculatorStub(channel)

# setup reflection
reflection_db = ProtoReflectionDescriptorDatabase(channel)
desc_pool = DescriptorPool(reflection_db)

# pick random service
services = list(reflection_db.get_services())
services = [x for x in services if not x.startswith("grpc")]
print("Available services:", *services, sep="\n")
service_name = random.choice(services)
print("Chosen: ", service_name)
service_desc = desc_pool.FindServiceByName(service_name)

# pick random method
print("\nAvailable Methods: ", *service_desc.methods_by_name.keys(), sep="\n")
method_descriptor: MethodDescriptor = random.choice(service_desc.methods_by_name.values())
print("Chosen: ", method_descriptor.name)

# pick random arguments
input_struct: Descriptor = method_descriptor.input_type
kwargs = {}
for field_descriptor in input_struct.fields:
    kwargs[field_descriptor.name] = get_random(field_descriptor)

print("\nArguments: ", kwargs, sep=" ")

# Create message object
message_class = GetMessageClass(input_struct)
class_arguments = message_class(**kwargs)
stub = basic_stub if service_name == "calculator.Calculator" else advanced_stub
result = stub.__getattribute__(method_descriptor.name)(class_arguments)
print("\nResult:\n", result)

channel.close()