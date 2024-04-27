package main

import (
	"context"
	"flag"
	"fmt"
	"log"
	"net"

	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
)

var port = flag.Int("port", 50051, "the port to serve on")

type calcServer struct {
	UnimplementedCalculatorServer
}

type advCalcServer struct {
	UnimplementedAdvancedCalculatorServer
}

// Add implements helloworld.GreeterServer
func (s *calcServer) AddTwo(ctx context.Context, in *ArithmeticOpArguments) (*SingleIntResult, error) {
	fmt.Println("AddTwo")
	return &SingleIntResult{
		Res: in.Arg1 + in.Arg2,
	}, nil
}

func (s *calcServer) SubtractTwo(ctx context.Context, in *ArithmeticOpArguments) (*SingleIntResult, error) {
	fmt.Println("SubtractTwo")
	return &SingleIntResult{
		Res: in.Arg1 - in.Arg2,
	}, nil
}

func (s *advCalcServer) ListSum(ctx context.Context, in *ListArithmeticOpArguments) (*SingleIntResult, error) {
	fmt.Println("ListSum")
	res := int32(0)
	for _, num := range in.Args {
		res += num
	}

	return &SingleIntResult{
		Res: res,
	}, nil
}

func (s *advCalcServer) MultiplyTwo(ctx context.Context, in *ArithmeticOpArguments) (*SingleIntResult, error) {
	fmt.Println("Multiply")
	return &SingleIntResult{
		Res: in.Arg1 * in.Arg2,
	}, nil
}

func (s *advCalcServer) ListSumComplex(ctx context.Context, in *ListComplexOpArguments) (*Complex, error) {
	fmt.Println("ListSumComplex")
	res := Complex{Real: 0, Imag: 0}
	for _, num := range in.Args {
		res.Real += num.Real
		res.Imag += num.Imag
	}

	return &res, nil
}

func main() {
	/*
		Install compiler
		go install google.golang.org/protobuf/cmd/protoc-gen-go
		go install google.golang.org/grpc/cmd/protoc-gen-go-grpc

		Compile calculator.proto using
		protoc --go_out=. --go_opt=paths=source_relative --go-grpc_out=. --go-grpc_opt=paths=source_relative calculator.proto

		Run using
		go run /.

		Fix module errors using
		go mod tidy
		I don't know what magic this command uses, but it should work.
	*/

	flag.Parse()
	lis, err := net.Listen("tcp", fmt.Sprintf("127.0.0.9:%d", *port))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	fmt.Printf("server listening at %v\n", lis.Addr())

	s := grpc.NewServer()

	// Register Greeter on the server.
	RegisterCalculatorServer(s, &calcServer{})
	RegisterAdvancedCalculatorServer(s, &advCalcServer{})

	// Register reflection service on gRPC server.
	reflection.Register(s)

	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
