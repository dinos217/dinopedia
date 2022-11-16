package com.project.dinopedia.enums;

import lombok.Getter;

@Getter
public enum Size {

    TINY("Tiny"),
    SMALL("Small"),
    MEDIUM("Medium"),
    BIG("Big"),
    HUGE("Huge");

    private final String label;

    Size(String label) {
        this.label = label;
    }

}
