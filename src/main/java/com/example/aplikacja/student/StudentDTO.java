package com.example.aplikacja.student;

import com.example.aplikacja.appuser.AppUserRole;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
