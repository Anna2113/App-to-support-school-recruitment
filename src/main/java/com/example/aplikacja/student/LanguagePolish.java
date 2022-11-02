package com.example.aplikacja.student;

import lombok.Getter;

@Getter
public enum LanguagePolish {

    YESPOLISH("TAK", Boolean.TRUE),
    NOPOLISH("NIE", Boolean.FALSE);

    private String label;
    private Boolean value;

    LanguagePolish(String label, Boolean value) {
        this.label = label;
        this.value = value;
    }


}
