package com.example.microservices.channel_service.service;

import com.example.microservices.grpc.GetUserRequest;
import com.example.microservices.grpc.UserResponse;
import com.example.microservices.grpc.UserServiceGrpcGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGrpcClientService {

    @GrpcClient("userServiceGrpc") // Tên client phải khớp với cấu hình trong application.properties
    private UserServiceGrpcGrpc.UserServiceGrpcBlockingStub userServiceBlockingStub;

    public Optional<com.example.microservices.channel_service.model.User> getUserById(Long userId) {
        GetUserRequest request = GetUserRequest.newBuilder().setUserId(userId).build();
        try {
            UserResponse response = userServiceBlockingStub.getUserById(request);
            if (response.getId() > 0) {
                com.example.microservices.channel_service.model.User user =
                        new com.example.microservices.channel_service.model.User(
                                response.getId(),
                                response.getUsername(),
                                response.getEmail()
                        );
                return Optional.of(user);
            }
        } catch (Exception e) {
            System.err.println("Error calling gRPC User Service: " + e.getMessage());
        }
        return Optional.empty();
    }
}
