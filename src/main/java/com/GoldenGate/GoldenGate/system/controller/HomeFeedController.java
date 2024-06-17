package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.system.DTO.HomeFeedDTO;
import com.GoldenGate.GoldenGate.system.service.HomeFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/homefeed")
public class HomeFeedController {

    private final HomeFeedService homeFeedService;

    @Autowired
    public HomeFeedController(HomeFeedService homeFeedService) {
        this.homeFeedService = homeFeedService;
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<HomeFeedDTO>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Page<HomeFeedDTO> homeFeedDTOPage = homeFeedService.getAllPosts(page, size);
            return ResponseEntity.ok(homeFeedDTOPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
