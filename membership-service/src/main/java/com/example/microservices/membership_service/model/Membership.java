package com.example.microservices.membership_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // Liên kết với User
    private Long channelId; // Liên kết với Channel
    private String role; // Ví dụ: OWNER, MEMBER, MODERATOR

    // Constructors
    public Membership() {
    }

    public Membership(Long userId, Long channelId, String role) {
        this.userId = userId;
        this.channelId = channelId;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", userId=" + userId +
                ", channelId=" + channelId +
                ", role='" + role + '\'' +
                '}';
    }
}
