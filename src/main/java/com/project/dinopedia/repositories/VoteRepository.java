package com.project.dinopedia.repositories;

import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.entities.User;
import com.project.dinopedia.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Boolean existsByUserAndDinosaur(User user, Dinosaur dinosaur);
}
