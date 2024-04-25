// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: calculator.proto
// Protobuf Java Version: 4.26.1

package sr.grpc.gen;

public final class CalculatorProto {
  private CalculatorProto() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 1,
      /* suffix= */ "",
      CalculatorProto.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_ArithmeticOpArguments_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_calculator_ArithmeticOpArguments_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_ArithmeticOpResult_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_calculator_ArithmeticOpResult_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_MultiplyArguments_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_calculator_MultiplyArguments_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_ComplexArithmeticOpArguments_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_calculator_ComplexArithmeticOpArguments_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_ComplexArithmeticOpResult_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_calculator_ComplexArithmeticOpResult_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020calculator.proto\022\ncalculator\"3\n\025Arithm" +
      "eticOpArguments\022\014\n\004arg1\030\001 \001(\005\022\014\n\004arg2\030\002 " +
      "\001(\005\"!\n\022ArithmeticOpResult\022\013\n\003res\030\001 \001(\005\"$" +
      "\n\021MultiplyArguments\022\017\n\007numbers\030\001 \003(\005\"W\n\034" +
      "ComplexArithmeticOpArguments\022)\n\006optype\030\001" +
      " \001(\0162\031.calculator.OperationType\022\014\n\004args\030" +
      "\002 \003(\001\"(\n\031ComplexArithmeticOpResult\022\013\n\003re" +
      "s\030\001 \001(\001*3\n\rOperationType\022\007\n\003SUM\020\000\022\007\n\003AVG" +
      "\020\001\022\007\n\003MIN\020\002\022\007\n\003MAX\020\0032\366\001\n\nCalculator\022J\n\003A" +
      "dd\022!.calculator.ArithmeticOpArguments\032\036." +
      "calculator.ArithmeticOpResult\"\000\022O\n\010Subtr" +
      "act\022!.calculator.ArithmeticOpArguments\032\036" +
      ".calculator.ArithmeticOpResult\"\000\022K\n\010Mult" +
      "iply\022\035.calculator.MultiplyArguments\032\036.ca" +
      "lculator.ArithmeticOpResult\"\0002{\n\022Advance" +
      "dCalculator\022e\n\020ComplexOperation\022(.calcul" +
      "ator.ComplexArithmeticOpArguments\032%.calc" +
      "ulator.ComplexArithmeticOpResult\"\000B \n\013sr" +
      ".grpc.genB\017CalculatorProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_calculator_ArithmeticOpArguments_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_calculator_ArithmeticOpArguments_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_calculator_ArithmeticOpArguments_descriptor,
        new java.lang.String[] { "Arg1", "Arg2", });
    internal_static_calculator_ArithmeticOpResult_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_calculator_ArithmeticOpResult_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_calculator_ArithmeticOpResult_descriptor,
        new java.lang.String[] { "Res", });
    internal_static_calculator_MultiplyArguments_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_calculator_MultiplyArguments_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_calculator_MultiplyArguments_descriptor,
        new java.lang.String[] { "Numbers", });
    internal_static_calculator_ComplexArithmeticOpArguments_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_calculator_ComplexArithmeticOpArguments_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_calculator_ComplexArithmeticOpArguments_descriptor,
        new java.lang.String[] { "Optype", "Args", });
    internal_static_calculator_ComplexArithmeticOpResult_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_calculator_ComplexArithmeticOpResult_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_calculator_ComplexArithmeticOpResult_descriptor,
        new java.lang.String[] { "Res", });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}