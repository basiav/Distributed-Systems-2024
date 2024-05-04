package main

import (
	"context"
	"flag"
	"fmt"
	"log"
	"math"
	"net"

	grpc "google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
)

var port = flag.Int("port", 50050, "the port to serve on")

type calcServer struct {
	UnimplementedCalculatorServer
}

// Add implements helloworld.GreeterServer
func (s *calcServer) SimpleAdd(ctx context.Context, in *ArithmeticOpArguments) (*SingleIntResult, error) {
	fmt.Println("SimpleAdd")
	return &SingleIntResult{
		Res: in.Arg1 + in.Arg2,
	}, nil
}

func (s *calcServer) SimpleSubtract(ctx context.Context, in *ArithmeticOpArguments) (*SingleIntResult, error) {
	fmt.Println("SimpleSubtract")
	return &SingleIntResult{
		Res: in.Arg1 - in.Arg2,
	}, nil
}

type advCalcServer struct {
	UnimplementedAdvancedCalculatorServer
}

func (s *advCalcServer) ComplexOperation(ctx context.Context, in *ComplexArithmeticOpArguments) (*ComplexArithmeticOpResult, error) {
	fmt.Println("ComplexOperation")
	fmt.Println(in.Optype, in.Args)
	res := float64(0)
	if in.Optype == OperationType_SUM {
		for _, num := range in.Args {
			res += num
		}
	}
	if in.Optype == OperationType_AVG {
		i := float64(0)
		for _, num := range in.Args {
			res += num
			i += 1
		}
		res /= i
	}
	if in.Optype == OperationType_MAX {
		max := math.Inf(-1)
		for _, num := range in.Args {
			if num >= max {
				max = num
				res = max
			}
		}
	}
	if in.Optype == OperationType_MIN {
		min := math.Inf(0)
		for _, num := range in.Args {
			if num < min {
				min = num
				res = min
			}
		}
	}
	return &ComplexArithmeticOpResult{Res: res}, nil
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
