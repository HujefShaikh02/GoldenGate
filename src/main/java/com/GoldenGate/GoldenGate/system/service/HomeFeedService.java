package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.DTO.HomeFeedDTO;
import org.springframework.data.domain.Page;

public interface HomeFeedService {


    Page<HomeFeedDTO> getAllPosts(int page, int size);
}
