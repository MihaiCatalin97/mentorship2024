package org.java.mentorship.contracts.user.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SessionWithKey extends Session {
    private String sessionKey;
}
