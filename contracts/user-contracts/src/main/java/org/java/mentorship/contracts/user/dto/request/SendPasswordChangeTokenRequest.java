package org.java.mentorship.contracts.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendPasswordChangeTokenRequest {

    private Integer userId;
    private String email;

}
