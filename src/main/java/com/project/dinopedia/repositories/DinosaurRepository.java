package com.project.dinopedia.repositories;

import com.project.dinopedia.entities.Dinosaur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DinosaurRepository extends JpaRepository<Dinosaur, Long> {

    Page<Dinosaur> findAll(Pageable pageable);

    Optional<Dinosaur> findByName(String name);
}
