package com.example.aplikacja.student.enums;

import lombok.Getter;

@Getter
public enum Ability {

    TAK("TAK"),
    NIE("NIE");

    private String label;


    Ability(String label) {
        this.label = label;
    }
}
