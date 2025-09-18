package com.example.microservices.membership_service.controller;

import com.example.microservices.membership_service.model.Membership;
import com.example.microservices.membership_service.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    @Autowired
    private MembershipRepository membershipRepository;

    // Get all memberships
    @GetMapping
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    // Get membership by ID
    @GetMapping("/{id}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable Long id) {
        Optional<Membership> membership = membershipRepository.findById(id);
        return membership.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get memberships by userId
    @GetMapping("/user/{userId}")
    public List<Membership> getMembershipsByUserId(@PathVariable Long userId) {
        return membershipRepository.findByUserId(userId);
    }

    // Get memberships by channelId
    @GetMapping("/channel/{channelId}")
    public List<Membership> getMembershipsByChannelId(@PathVariable Long channelId) {
        return membershipRepository.findByChannelId(channelId);
    }

    // Create a new membership
    @PostMapping
    public ResponseEntity<Membership> createMembership(@RequestBody Membership membership) {
        Membership savedMembership = membershipRepository.save(membership);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMembership);
    }

    // Update an existing membership
    @PutMapping("/{id}")
    public ResponseEntity<Membership> updateMembership(@PathVariable Long id, @RequestBody Membership membershipDetails) {
        Optional<Membership> membershipOptional = membershipRepository.findById(id);
        if (membershipOptional.isPresent()) {
            Membership membership = membershipOptional.get();
            membership.setUserId(membershipDetails.getUserId());
            membership.setChannelId(membershipDetails.getChannelId());
            membership.setRole(membershipDetails.getRole());
            Membership updatedMembership = membershipRepository.save(membership);
            return ResponseEntity.ok(updatedMembership);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a membership
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        if (membershipRepository.existsById(id)) {
            membershipRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
