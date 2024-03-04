package com.cs206.g2t2.models.team;

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
        OWNER("Owner", 2),
        ADMIN("Admin", 1),
        MEMBER("Member", 0);

        public final String label;
        public final int value;

        Role(String label, int value) {
            this.label = label;
            this.value = value;
        }

        public boolean isHigherRole(Role role) {
            return this.value > role.value;
        }

        public boolean isEqualRole(Role role) {
            return this.value == role.value;
        }
    }

    private String userId;
    private Role role;
    private LocalDateTime joinDate;
}
