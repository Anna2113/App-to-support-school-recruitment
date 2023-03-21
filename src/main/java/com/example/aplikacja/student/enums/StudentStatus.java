package com.example.aplikacja.student.enums;

import lombok.Getter;

@Getter
public enum StudentStatus {
    sklasyfikowany("sklasyfikowany"),
    rezerwowy("rezerwowy");

    private String label;

    StudentStatus(String label) {
        this.label = label;
    }
}
