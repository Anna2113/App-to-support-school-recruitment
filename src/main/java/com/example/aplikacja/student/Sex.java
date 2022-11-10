package com.example.aplikacja.student;

import lombok.Getter;

@Getter
public enum Sex {
    Kobieta("Kobieta"),
    Mężczyzna("Mężczyzna");

    private String label;

    Sex(String label) {
        this.label = label;
    }
}
