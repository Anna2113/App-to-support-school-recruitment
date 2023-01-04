package com.example.aplikacja.student.enums;

import lombok.Getter;

@Getter
public enum LanguagePolish {

    TAK("TAK"),
    NIE("NIE");

    private String label;

    LanguagePolish(String label) {
        this.label = label;
    }


}
