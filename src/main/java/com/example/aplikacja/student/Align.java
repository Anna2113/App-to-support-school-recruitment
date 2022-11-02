package com.example.aplikacja.student;

import lombok.Getter;

@Getter
public enum Align {

    ALIGN("TAK", Boolean.TRUE),
    NOTALIGN("NIE", Boolean.FALSE);

    private String label;
    private Boolean value;

    Align(String label, Boolean value) {
        this.label = label;
        this.value = value;
    }

}
