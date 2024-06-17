package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.config.JwtService;
import com.GoldenGate.GoldenGate.repository.UserRepository;
import com.GoldenGate.GoldenGate.system.DTO.ProfileDTO;
import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.system.repository.ProfileRepository;
import com.GoldenGate.GoldenGate.system.service.ProfileService;
import com.GoldenGate.GoldenGate.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

//@CrossOrigin(origins = "* ", allowedHeaders = "*")
//@CrossOrigin(origins = "* ", allowedHeaders = "*")
@CrossOrigin("*")
@RestController

@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final JwtService JwtService;

    private final ProfileService profileService;

    private final ProfileRepository profileRepository;
    // private final UserDetailsService userDetailsService;

    private final UserRepository repository;

    @Autowired
    public ProfileController(JwtService jwtService, ProfileService profileService, UserDetailsService userDetailsService, ProfileRepository profileRepository, UserRepository repository) {
        this.JwtService = jwtService;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        //this.userDetailsService = userDetailsService;
        this.repository = repository;
    }

    // Endpoint to retrieve profile by JWT token

    @PostMapping("")
    public ResponseEntity<Profile> createProfile(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,
                                                 @NonNull FilterChain filterChain,
                                                 @RequestBody Profile newProfile) throws ServletException, IOException {

        try {
            //System.out.println("in profile creation");
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }
            //System.out.println("after final variable assign");
            jwt = authHeader.substring(7);
            //System.out.println(jwt+" this is jwt");
            userEmail = JwtService.extractUsername(jwt);
            // System.out.println(userEmail+" this is user email");
            if (userEmail != null ){

                // System.out.println("in if user load by optional");
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                System.out.println("optionalUser "+optionalUser);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");

                }User userDetails = optionalUser.get();
                // System.out.println("this is user details loaded "+userDetails);
                // System.out.println(" User userDetails in profile creation"+userDetails);

                int Userid= Math.toIntExact(userDetails.getUserId());
                Profile createdProfile = profileService.createNewProfile(userDetails, newProfile);

                return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
            }

        } catch (Exception e) {
            System.out.println("try catch "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return null;
    }

    @GetMapping("/me")
    public ResponseEntity<Profile> getProfileByJwtToken(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,
                                                        @NonNull FilterChain filterChain) {
       /* Profile profile = profileService.getProfileByJwtToken(jwtToken);

        if (profile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        */ try {
            //System.out.println("in profile creation");
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }
            //System.out.println("after final variable assign");
            jwt = authHeader.substring(7);
            //System.out.println(jwt+" this is jwt");
            userEmail = JwtService.extractUsername(jwt);
            // System.out.println(userEmail+" this is user email");
            if (userEmail != null ){

                // System.out.println("in if user load by optional");
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                System.out.println("optionalUser "+optionalUser);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");

                }
                User userDetails = optionalUser.get();
                // System.out.println("this is user details loaded "+userDetails);
                // System.out.println(" User userDetails in profile creation"+userDetails);

                int Userid= Math.toIntExact(userDetails.getUserId());
                Profile profile = profileRepository.findByUser_UserId(Userid);
                if (profile == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }


                return ResponseEntity.ok(profile);
            }

        } catch (Exception e) {
            System.out.println("try catch "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return null;



    }

    @GetMapping("")
    public ResponseEntity<Page<Profile>> getAllProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Profile> profilesPage = (Page<Profile>) profileRepository.findAll(pageable);

            if (profilesPage.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(profilesPage);
        } catch (Exception e) {
            System.out.println("Exception while fetching profiles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(
            @PathVariable Integer profileId,
            @RequestBody Profile updatedProfile) {
        try {
            // Update profile using the service
            Profile updated = profileService.updateProfile(profileId, updatedProfile);

            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfileDetails(HttpServletRequest request) {
        try {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            String jwt = authHeader.substring(7);
            String userEmail = JwtService.extractUsername(jwt);

            if (userEmail != null) {
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                if (!optionalUser.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                User user = optionalUser.get();
                Profile profile = profileRepository.findByUser_UserId(user.getUserId());
                if (profile == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                // Convert Profile entity to ProfileDTO
                ProfileDTO profileDTO = convertToDTO(profile, user.getEmail());

                return ResponseEntity.ok(profileDTO);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Integer profileId) {
        Optional<Profile> profileOptional = profileRepository.findById(profileId);
        if (!profileOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Profile profile = profileOptional.get();
        User user = profile.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProfileDTO profileDTO = convertToDTO(profile, user.getEmail());
        return ResponseEntity.ok(profileDTO);
    }



    private ProfileDTO convertToDTO(Profile profile, String email) {

        return ProfileDTO.builder()
                .profileId(profile.getProfileId())
                .avatar(profile.getAvatar())
                .bio(profile.getBio())
                .fullName(profile.getFullName())
                .otherDetails(profile.getOtherDetails())
                .userId(profile.getUser().getUserId())
                .backgroundImage(profile.getBackgroundImage())
                .profileType(profile.getProfileType())
                .email(email)
                .build();
    }


}
