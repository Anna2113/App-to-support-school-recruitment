package com.example.aplikacja.student.enums;

import lombok.Getter;

@Getter
public enum NameOfClass {
    MatGeoInf("MatGeoInf"),
    Humanistyczna("Humanistyczna"),
    Lingwistyczna("Lingwistyczna"),
    Sportowa("Sportowa"),
    BiolChem("BiolChem"),
    Artystyczna("Artystyczna");

    private String label;

    NameOfClass(String label) {
        this.label = label;
    }
}
