package com.example.aplikacja.student;

import lombok.Getter;

@Getter
public enum Sex {
    Female("Kobieta"),
    Male("Mężczyzna");

    private String label;

    Sex(String label) {
        this.label = label;
    }
}
