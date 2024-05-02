// Code generated by protoc-gen-go-grpc. DO NOT EDIT.
// versions:
// - protoc-gen-go-grpc v1.3.0
// - protoc             v5.26.1
// source: calculator.proto

package main

import (
	context "context"
	grpc "google.golang.org/grpc"
	codes "google.golang.org/grpc/codes"
	status "google.golang.org/grpc/status"
)

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
// Requires gRPC-Go v1.32.0 or later.
const _ = grpc.SupportPackageIsVersion7

const (
	Calculator_SimpleAdd_FullMethodName      = "/calculator.Calculator/SimpleAdd"
	Calculator_SimpleSubtract_FullMethodName = "/calculator.Calculator/SimpleSubtract"
)

// CalculatorClient is the client API for Calculator service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type CalculatorClient interface {
	SimpleAdd(ctx context.Context, in *ArithmeticOpArguments, opts ...grpc.CallOption) (*SingleIntResult, error)
	SimpleSubtract(ctx context.Context, in *ArithmeticOpArguments, opts ...grpc.CallOption) (*SingleIntResult, error)
}

type calculatorClient struct {
	cc grpc.ClientConnInterface
}

func NewCalculatorClient(cc grpc.ClientConnInterface) CalculatorClient {
	return &calculatorClient{cc}
}

func (c *calculatorClient) SimpleAdd(ctx context.Context, in *ArithmeticOpArguments, opts ...grpc.CallOption) (*SingleIntResult, error) {
	out := new(SingleIntResult)
	err := c.cc.Invoke(ctx, Calculator_SimpleAdd_FullMethodName, in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *calculatorClient) SimpleSubtract(ctx context.Context, in *ArithmeticOpArguments, opts ...grpc.CallOption) (*SingleIntResult, error) {
	out := new(SingleIntResult)
	err := c.cc.Invoke(ctx, Calculator_SimpleSubtract_FullMethodName, in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// CalculatorServer is the server API for Calculator service.
// All implementations must embed UnimplementedCalculatorServer
// for forward compatibility
type CalculatorServer interface {
	SimpleAdd(context.Context, *ArithmeticOpArguments) (*SingleIntResult, error)
	SimpleSubtract(context.Context, *ArithmeticOpArguments) (*SingleIntResult, error)
	mustEmbedUnimplementedCalculatorServer()
}

// UnimplementedCalculatorServer must be embedded to have forward compatible implementations.
type UnimplementedCalculatorServer struct {
}

func (UnimplementedCalculatorServer) SimpleAdd(context.Context, *ArithmeticOpArguments) (*SingleIntResult, error) {
	return nil, status.Errorf(codes.Unimplemented, "method SimpleAdd not implemented")
}
func (UnimplementedCalculatorServer) SimpleSubtract(context.Context, *ArithmeticOpArguments) (*SingleIntResult, error) {
	return nil, status.Errorf(codes.Unimplemented, "method SimpleSubtract not implemented")
}
func (UnimplementedCalculatorServer) mustEmbedUnimplementedCalculatorServer() {}

// UnsafeCalculatorServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to CalculatorServer will
// result in compilation errors.
type UnsafeCalculatorServer interface {
	mustEmbedUnimplementedCalculatorServer()
}

func RegisterCalculatorServer(s grpc.ServiceRegistrar, srv CalculatorServer) {
	s.RegisterService(&Calculator_ServiceDesc, srv)
}

func _Calculator_SimpleAdd_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(ArithmeticOpArguments)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(CalculatorServer).SimpleAdd(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: Calculator_SimpleAdd_FullMethodName,
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(CalculatorServer).SimpleAdd(ctx, req.(*ArithmeticOpArguments))
	}
	return interceptor(ctx, in, info, handler)
}

func _Calculator_SimpleSubtract_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(ArithmeticOpArguments)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(CalculatorServer).SimpleSubtract(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: Calculator_SimpleSubtract_FullMethodName,
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(CalculatorServer).SimpleSubtract(ctx, req.(*ArithmeticOpArguments))
	}
	return interceptor(ctx, in, info, handler)
}

// Calculator_ServiceDesc is the grpc.ServiceDesc for Calculator service.
// It's only intended for direct use with grpc.RegisterService,
// and not to be introspected or modified (even as a copy)
var Calculator_ServiceDesc = grpc.ServiceDesc{
	ServiceName: "calculator.Calculator",
	HandlerType: (*CalculatorServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "SimpleAdd",
			Handler:    _Calculator_SimpleAdd_Handler,
		},
		{
			MethodName: "SimpleSubtract",
			Handler:    _Calculator_SimpleSubtract_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "calculator.proto",
}

const (
	AdvancedCalculator_ComplexOperation_FullMethodName = "/calculator.AdvancedCalculator/ComplexOperation"
	AdvancedCalculator_ListSum_FullMethodName          = "/calculator.AdvancedCalculator/ListSum"
)

// AdvancedCalculatorClient is the client API for AdvancedCalculator service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type AdvancedCalculatorClient interface {
	ComplexOperation(ctx context.Context, in *ComplexArithmeticOpArguments, opts ...grpc.CallOption) (*ComplexArithmeticOpResult, error)
	ListSum(ctx context.Context, in *ListArithmeticOpArguments, opts ...grpc.CallOption) (*SingleIntResult, error)
}

type advancedCalculatorClient struct {
	cc grpc.ClientConnInterface
}

func NewAdvancedCalculatorClient(cc grpc.ClientConnInterface) AdvancedCalculatorClient {
	return &advancedCalculatorClient{cc}
}

func (c *advancedCalculatorClient) ComplexOperation(ctx context.Context, in *ComplexArithmeticOpArguments, opts ...grpc.CallOption) (*ComplexArithmeticOpResult, error) {
	out := new(ComplexArithmeticOpResult)
	err := c.cc.Invoke(ctx, AdvancedCalculator_ComplexOperation_FullMethodName, in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *advancedCalculatorClient) ListSum(ctx context.Context, in *ListArithmeticOpArguments, opts ...grpc.CallOption) (*SingleIntResult, error) {
	out := new(SingleIntResult)
	err := c.cc.Invoke(ctx, AdvancedCalculator_ListSum_FullMethodName, in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// AdvancedCalculatorServer is the server API for AdvancedCalculator service.
// All implementations must embed UnimplementedAdvancedCalculatorServer
// for forward compatibility
type AdvancedCalculatorServer interface {
	ComplexOperation(context.Context, *ComplexArithmeticOpArguments) (*ComplexArithmeticOpResult, error)
	ListSum(context.Context, *ListArithmeticOpArguments) (*SingleIntResult, error)
	mustEmbedUnimplementedAdvancedCalculatorServer()
}

// UnimplementedAdvancedCalculatorServer must be embedded to have forward compatible implementations.
type UnimplementedAdvancedCalculatorServer struct {
}

func (UnimplementedAdvancedCalculatorServer) ComplexOperation(context.Context, *ComplexArithmeticOpArguments) (*ComplexArithmeticOpResult, error) {
	return nil, status.Errorf(codes.Unimplemented, "method ComplexOperation not implemented")
}
func (UnimplementedAdvancedCalculatorServer) ListSum(context.Context, *ListArithmeticOpArguments) (*SingleIntResult, error) {
	return nil, status.Errorf(codes.Unimplemented, "method ListSum not implemented")
}
func (UnimplementedAdvancedCalculatorServer) mustEmbedUnimplementedAdvancedCalculatorServer() {}

// UnsafeAdvancedCalculatorServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to AdvancedCalculatorServer will
// result in compilation errors.
type UnsafeAdvancedCalculatorServer interface {
	mustEmbedUnimplementedAdvancedCalculatorServer()
}

func RegisterAdvancedCalculatorServer(s grpc.ServiceRegistrar, srv AdvancedCalculatorServer) {
	s.RegisterService(&AdvancedCalculator_ServiceDesc, srv)
}

func _AdvancedCalculator_ComplexOperation_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(ComplexArithmeticOpArguments)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(AdvancedCalculatorServer).ComplexOperation(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: AdvancedCalculator_ComplexOperation_FullMethodName,
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(AdvancedCalculatorServer).ComplexOperation(ctx, req.(*ComplexArithmeticOpArguments))
	}
	return interceptor(ctx, in, info, handler)
}

func _AdvancedCalculator_ListSum_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(ListArithmeticOpArguments)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(AdvancedCalculatorServer).ListSum(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: AdvancedCalculator_ListSum_FullMethodName,
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(AdvancedCalculatorServer).ListSum(ctx, req.(*ListArithmeticOpArguments))
	}
	return interceptor(ctx, in, info, handler)
}

// AdvancedCalculator_ServiceDesc is the grpc.ServiceDesc for AdvancedCalculator service.
// It's only intended for direct use with grpc.RegisterService,
// and not to be introspected or modified (even as a copy)
var AdvancedCalculator_ServiceDesc = grpc.ServiceDesc{
	ServiceName: "calculator.AdvancedCalculator",
	HandlerType: (*AdvancedCalculatorServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "ComplexOperation",
			Handler:    _AdvancedCalculator_ComplexOperation_Handler,
		},
		{
			MethodName: "ListSum",
			Handler:    _AdvancedCalculator_ListSum_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "calculator.proto",
}