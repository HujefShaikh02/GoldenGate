package com.GoldenGate.GoldenGate.system.DTO;

import com.GoldenGate.GoldenGate.system.Enum.ProfileType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {

    private Integer profileId;
    private String avatar;
    private String bio;
    private String fullName;
    private String otherDetails;
    private Integer userId; // Assuming 'User' has an 'id' field
    private String backgroundImage;
    private ProfileType profileType;
    private String email;

    // Add any additional methods you need here
}
