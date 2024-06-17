package com.GoldenGate.GoldenGate.system.repository;
import com.GoldenGate.GoldenGate.system.model.Experience;
import com.GoldenGate.GoldenGate.system.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {


   // Experience findByUserId(int userId);

    //Experience findByUser_UserId(int userId);
    List<Experience> findByUser_UserId(Integer userId);

   // List<Experience> findByUserId(int userId);

    //  List<Experience> findByUserId(int userId);
}

