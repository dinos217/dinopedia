package com.project.dinopedia.enums;

import lombok.Getter;

@Getter
public enum Period {

    TRIASSIC("Triassic"),
    JURASSIC("Jurassic"),
    CRETACEOUS("Cretaceous");

    private final String label;

    Period(String label) {
        this.label = label;
    }
}
