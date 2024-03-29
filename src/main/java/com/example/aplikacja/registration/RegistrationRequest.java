package com.example.aplikacja.registration;



import com.example.aplikacja.appuser.AppUserRole;
import com.example.aplikacja.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final AppUserRole appUserRole;
    private final Student student;
}
