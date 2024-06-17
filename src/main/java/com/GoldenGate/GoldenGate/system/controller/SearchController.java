package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.system.DTO.SearchDTO;
import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.system.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final ProfileRepository profileRepository;

    @Autowired
    public SearchController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping("/profiles")
    public ResponseEntity<Page<SearchDTO>> searchProfilesByFullName(
            @RequestParam String keyword,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Profile> profilePage = profileRepository.findByFullNameContaining(keyword, pageable);
        Page<SearchDTO> searchDTOPage = profilePage.map(this::convertToSearchDTO);

        return ResponseEntity.ok(searchDTOPage);
    }

    private SearchDTO convertToSearchDTO(Profile profile) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setProfileId(profile.getProfileId());
        searchDTO.setFullName(profile.getFullName());
        searchDTO.setAvatar(profile.getAvatar());
        // Set other fields if necessary
        return searchDTO;
    }
}
