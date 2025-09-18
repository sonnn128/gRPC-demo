package com.example.microservices.user_service.service;

import com.example.microservices.grpc.GetUserRequest;
import com.example.microservices.grpc.UserResponse;
import com.example.microservices.grpc.UserServiceGrpcGrpc;
import com.example.microservices.user_service.model.User;
import com.example.microservices.user_service.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GrpcService
public class UserGrpcService extends UserServiceGrpcGrpc.UserServiceGrpcImplBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void getUserById(GetUserRequest request, StreamObserver<UserResponse> responseObserver) {
        Long userId = request.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);

        UserResponse.Builder responseBuilder = UserResponse.newBuilder();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            responseBuilder.setId(user.getId())
                    .setUsername(user.getUsername())
                    .setEmail(user.getEmail());
        } else {
            // Xử lý trường hợp không tìm thấy người dùng
            // Có thể trả về lỗi gRPC hoặc một UserResponse rỗng/default
            // Để đơn giản, chúng ta sẽ trả về một UserResponse với ID = 0 (hoặc không set các trường)
            // hoặc gửi một lỗi gRPC
            // Ví dụ: responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND));
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted(); // Đánh dấu rằng phản hồi đã hoàn thành
    }
}
