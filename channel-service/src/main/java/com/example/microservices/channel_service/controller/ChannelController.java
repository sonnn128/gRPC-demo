package com.example.microservices.channel_service.controller;

import com.example.microservices.channel_service.service.UserGrpcClientService;
import com.example.microservices.channel_service.model.Channel;
import com.example.microservices.channel_service.model.ChannelWithUser; // Lớp mới
import com.example.microservices.channel_service.model.User;
import com.example.microservices.channel_service.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserGrpcClientService userGrpcClientService; // Inject gRPC client service

    // Get all channels
    @GetMapping
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    // Get channel by ID
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable Long id) {
        Optional<Channel> channel = channelRepository.findById(id);
        return channel.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // NEW: Get channel by ID with owner user details (using gRPC)
    @GetMapping("/{id}/with-owner")
    public ResponseEntity<ChannelWithUser> getChannelWithUserById(@PathVariable Long id) {
        Optional<Channel> channelOptional = channelRepository.findById(id);

        if (channelOptional.isPresent()) {
            Channel channel = channelOptional.get();
            Optional<User> ownerUserOptional = userGrpcClientService.getUserById(channel.getOwnerUserId());

            ChannelWithUser channelWithUser = new ChannelWithUser();
            channelWithUser.setChannel(channel);
            ownerUserOptional.ifPresent(channelWithUser::setOwnerUser);

            return ResponseEntity.ok(channelWithUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new channel
    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) {
        Channel savedChannel = channelRepository.save(channel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedChannel);
    }

    // Update an existing channel
    @PutMapping("/{id}")
    public ResponseEntity<Channel> updateChannel(@PathVariable Long id, @RequestBody Channel channelDetails) {
        Optional<Channel> channelOptional = channelRepository.findById(id);
        if (channelOptional.isPresent()) {
            Channel channel = channelOptional.get();
            channel.setName(channelDetails.getName());
            channel.setDescription(channelDetails.getDescription());
            channel.setOwnerUserId(channelDetails.getOwnerUserId());
            Channel updatedChannel = channelRepository.save(channel);
            return ResponseEntity.ok(updatedChannel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a channel
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        if (channelRepository.existsById(id)) {
            channelRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}