package com.example.aplikacja.student.enums;

import lombok.Getter;

@Getter
public enum Align {


    Obcokrajowiec("TAK"),
    Polak("NIE");

    private String label;

    Align(String label) {
        this.label = label;
    }

}
