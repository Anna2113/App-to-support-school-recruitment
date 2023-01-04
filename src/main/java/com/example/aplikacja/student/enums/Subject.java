package com.example.aplikacja.student.enums;

import lombok.Getter;

@Getter
public enum Subject {

    Polski("Język Polski"),
    Matematyka("Matematyka"),
    Angielski("Angielski"),
    Niemiecki("Niemiecki"),
    Francuski("Francuski"),
    Hiszpański("Hiszpański"),
    Włoski("Włoski"),
    Historia("Historia"),
    WOS("Wiedza o społeczeństwie"),
    Biologia("Biologia"),
    Chemia("Chemia"),
    Fizyka("Fizyka"),
    Geografia("Geografia"),
    Informatyka("Informatyka"),
    Technika("Technika"),
    Muzyka("Historia muzyki"),
    Plastyka("historia sztuki"),
    WF("Wychowanie fizyczne");
    private String label;

    Subject(String label) {
        this.label = label;
    }




}

