package com.example.aplikacja.student.dto;

import com.example.aplikacja.student.entity.Exam;
import com.example.aplikacja.student.enums.Sex;
import lombok.Data;

@Data
public class StudentDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private Sex sex;
    private Boolean align;
    private Boolean languagePolish;
}
