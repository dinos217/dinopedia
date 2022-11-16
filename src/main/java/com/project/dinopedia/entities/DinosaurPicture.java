package com.project.dinopedia.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dinosaur_picture")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class DinosaurPicture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "image_data", length = 1000)
    private byte[] imageData;

    @ManyToOne
    private Dinosaur dinosaur;
}
