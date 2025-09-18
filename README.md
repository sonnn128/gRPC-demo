```
gRPC:
Step 1: Pom.xml
		server: grpc.server.port=9090
		client: 
			grpc.client.userServiceGrpc.address=static://localhost:9090
			grpc.client.userServiceGrpc.negotiationType=plaintext
Step 2: main/proto/user_service.proto
Step 3: khởi tạo user_service.proto cho cả service client và service server và file này phải giống nhau ư
Step 4: Tạo UserGrpcService trong user_service
Step 5: tạo UserGrpcClientService trong client service(channel sservice)
Step 6: Tạo User.java Model trong channel-service (client service)
Step 7: Tạo UserGrpcClientService 
Step 8: sử dụng nếu cần UserGrpcClientService 


```