package org.java.mentorship.contracts.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SendPasswordChangeTokenRequest {

    private final Integer userId;

}
