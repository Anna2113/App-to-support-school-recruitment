package com.example.aplikacja.student.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public enum NameOfClass{
    MatGeoInf("MatGeoInf"),
    Humanistyczna("Humanistyczna"),
    MatAngNiem("MatAngNiem"),
    Sportowa("Sportowa"),
    BiolChem("BiolChem"),
    Artystyczna("Artystyczna"),
    FizChemFranc("FizChemFranc");


    private String label;

    NameOfClass(String label) {
        this.label = label;
    }
}
