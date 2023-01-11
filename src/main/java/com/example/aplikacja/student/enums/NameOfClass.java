package com.example.aplikacja.student.enums;

import lombok.Getter;

@Getter
public enum NameOfClass {
    MatGeoInf("MatGeoInf"),
    Humanistyczna("Humanistyczna"),
    MatAngNiem("MatAngNiem"),
    Sportowa("Sportowa"),
    BiolChem("BiolChem"),
    Muzyczna("Muzyczna"),
    Aktorska("Aktorska");

    private String label;

    NameOfClass(String label) {
        this.label = label;
    }
}
