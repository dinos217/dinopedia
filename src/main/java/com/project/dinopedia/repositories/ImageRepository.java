package com.project.dinopedia.repositories;

import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByDinosaur(Dinosaur dinosaur);

    Optional<Image> findByName(String name);
}
