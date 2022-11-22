package com.project.dinopedia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dinosaurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dinosaur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "eating_class")
    private String eatingClass;

    @Column(name = "period")
    private String period;

    @Column(name = "size")
    private String size;

    @JsonIgnore
    @OneToMany(mappedBy = "dinosaur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "dinosaur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> likes = new ArrayList<>();
}
