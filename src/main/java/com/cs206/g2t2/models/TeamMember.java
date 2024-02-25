package com.cs206.g2t2.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TeamMember {
    public enum Role {
        ADMIN,
        MEMBER,
    }

    private String userId;
    private Role role;
    private LocalDateTime joinDate;
}
