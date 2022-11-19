package com.project.dinopedia.repositories;

import com.project.dinopedia.entities.Dinosaur;
import com.project.dinopedia.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByDinosaur(Dinosaur dinosaur);
}
