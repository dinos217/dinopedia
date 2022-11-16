package com.project.dinopedia.repositories;

import com.project.dinopedia.entities.DinosaurPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinosaurPictureRepository extends JpaRepository<DinosaurPicture, Long> {
}
