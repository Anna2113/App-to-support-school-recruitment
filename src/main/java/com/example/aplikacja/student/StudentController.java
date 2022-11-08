package com.example.aplikacja.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    private StudentService studentService;

    @GetMapping("/studentForm") //przycisk "Dodaj ucznia na podstronie hello"
    public String addStudent(Model model) {
        model.addAttribute(new StudentDTO());
        model.addAttribute(new ExamDTO());
        return "/student/student";
    }

    @PostMapping("/student/student")
    public String add(StudentDTO student, ExamDTO exam,
                      GradeDTO grade, OlympiadDTO olympiad
                     , ExtraParametersDTO extparam,
                      Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student appUserWithEmail = studentService.findUserByEmail(student.getEmail()).orElse(null);
            if (appUserWithEmail == null) {
                studentService.addStudent(student,exam, grade, olympiad
                        , extparam
                );
                model.addAttribute("addedStudent",
                        "Student został dodany, przejdź do następnej zakładki.");
                return "/student/student";
            } else {
                model.addAttribute("emailExist", "Taki email już istnieje");
                return "/student/student";
            }
        }
    }

    @GetMapping("/aboutStudent/{id}")
    public String showMoreAboutStudent(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student student = studentService.findUserByEmail(principal.getName()).orElse(null);

            model.addAttribute("student", student);

            return "/student/moreAboutStudent";
        }
    }

    @GetMapping("/list/{id}")
    public String listOfStudents(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student student = studentService.findUserByEmail(principal.getName()).orElse(null);

            model.addAttribute("student", student);

            return "/thanksForSignIn";
        }
    }
}
