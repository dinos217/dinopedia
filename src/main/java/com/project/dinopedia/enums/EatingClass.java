package com.project.dinopedia.enums;

import lombok.Getter;

@Getter
public enum EatingClass {

    CARNIVORES("Carnivores"),
    HERBIVORES("Herbivores"),
    OMNIVORES("Omnivores");

    private final String label;

    EatingClass(String label) {
        this.label = label;
    }

}
