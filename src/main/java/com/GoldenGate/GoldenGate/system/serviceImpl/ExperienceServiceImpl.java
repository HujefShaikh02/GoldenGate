package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.DTO.ExperienceDTO;
import com.GoldenGate.GoldenGate.system.model.Experience;
import com.GoldenGate.GoldenGate.system.repository.ExperienceRepository;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.GoldenGate.GoldenGate.system.service.*;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Autowired
    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    public Experience saveExperience(User userDetails, Experience experience) {

        // Set the user to the new experience and save it
        experience.setUser(userDetails);
        return experienceRepository.save(experience);
    }


    @Override
    public Experience updateExperience(Integer id, Experience updatedExperience) {
        Optional<Experience> optionalExperience = experienceRepository.findById(id);
        if (optionalExperience.isPresent()) {
            Experience experience = optionalExperience.get();

            experience.setTitle(updatedExperience.getTitle());
            experience.setEmploymentType(updatedExperience.getEmploymentType());

            return experienceRepository.save(experience);
        } else {
            return null;
        }
    }

    @Override
    public void deleteExperience(Integer id) {
        experienceRepository.deleteById(id);
    }

    @Override
    public Experience getExperienceById(Integer id) {
        Optional<Experience> optionalExperience = experienceRepository.findById(id);
        return optionalExperience.orElse(null);
    }



    @Override
    public List<ExperienceDTO> getAllExperiences(int userId) {
        List<Experience> experiences = experienceRepository.findByUser_UserId(userId);
        return experiences.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Private methods for conversion

    private ExperienceDTO convertToDTO(Experience experience) {
        if (experience == null) {
            return null;
        }

        return ExperienceDTO.builder()
                .experienceId(experience.getExperienceId())
                .title(experience.getTitle())
                .employmentType(experience.getEmploymentType())
                .userId(experience.getUser() != null ? experience.getUser().getUserId() : null)
                .profileId(experience.getProfile() != null ? experience.getProfile().getProfileId() : null)
                .jobLocation(experience.getJobLocation())
                .locationType(experience.getLocationType())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .industry(experience.getIndustry() != null ? Math.toIntExact(experience.getIndustry().getId()) : null)
                .description(experience.getDescription())
                .profileHeadline(experience.getProfileHeadline())
                .build();
    }
    private Experience convertToEntity(ExperienceDTO experienceDTO) {
        return Experience.builder()
                .experienceId(experienceDTO.getExperienceId())
                .title(experienceDTO.getTitle())
                .employmentType(experienceDTO.getEmploymentType())
                .jobLocation(experienceDTO.getJobLocation())
                .locationType(experienceDTO.getLocationType())
                .startDate(experienceDTO.getStartDate())
                .endDate(experienceDTO.getEndDate())
                .description(experienceDTO.getDescription())
                .profileHeadline(experienceDTO.getProfileHeadline())
                .build();
    }
}
