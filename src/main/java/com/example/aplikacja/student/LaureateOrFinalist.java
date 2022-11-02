package com.example.aplikacja.student;

import lombok.Getter;

@Getter
public enum LaureateOrFinalist {

    YESFinalist("Finalista"),
    YESLaureate("Laureat"),
    NOResult("Brak");

    private String label;

    LaureateOrFinalist(String label) {
        this.label = label;
    }
}
