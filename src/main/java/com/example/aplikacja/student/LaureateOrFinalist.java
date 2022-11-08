package com.example.aplikacja.student;

import lombok.Getter;

@Getter
public enum LaureateOrFinalist {

    Finalista("Finalista"),
    Laureat("Laureat"),
    Brak("Brak");

    private String label;

    LaureateOrFinalist(String label) {
        this.label = label;
    }
}
