package com.GoldenGate.GoldenGate.system.DTO;

import com.GoldenGate.GoldenGate.system.Enum.EmploymentType;
import com.GoldenGate.GoldenGate.system.Enum.LocationType;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExperienceDTO {
    private Integer experienceId;
    private String title;
    private EmploymentType employmentType;
    private Integer userId;
    private Integer profileId;
    private String jobLocation;
    private LocationType locationType;
    private Date startDate;
    private Date endDate;
    private Integer industry;
    private String description;
    private String profileHeadline;
}