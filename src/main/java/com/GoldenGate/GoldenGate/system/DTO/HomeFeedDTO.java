package com.GoldenGate.GoldenGate.system.DTO;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeFeedDTO {

    private Integer profileId;
    private String fullName;
    private String avatar;


    private Long postId;
    private String content;
    private List<String> images;


}
