package com.example.aplikacja.student.enums;

import lombok.Getter;

@Getter
public enum LaureateOrFinalist {

    Laureat("Laureat"),
    Finalista("Finalista"),
    Brak("Brak");

    private String label;

    LaureateOrFinalist(String label) {
        this.label = label;
    }
}
