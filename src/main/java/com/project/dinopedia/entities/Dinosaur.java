package com.project.dinopedia.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dinosaur")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Dinosaur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "eating_class")
    private String eatingClass;

    @Column(name = "typical_colour")
    private String colour;

    @Column(name = "period")
    private String period;

    @Column(name = "average_size")
    private String size;

    @OneToMany(mappedBy = "dinosaur", cascade = CascadeType.ALL)
    private List<DinosaurPicture> pictures = new ArrayList<>();
}
