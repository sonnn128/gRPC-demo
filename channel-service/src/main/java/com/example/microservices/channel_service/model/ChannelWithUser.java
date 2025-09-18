package com.example.microservices.channel_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelWithUser {
    private Channel channel;
    private User ownerUser; // Thông tin người dùng sẽ được điền từ gRPC
}
