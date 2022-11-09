package com.example.aplikacja.student;

import com.example.aplikacja.appuser.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.*;

@Controller
public class StudentController {

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    private StudentService studentService;
    private AppUserService appUserService;

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
                studentService.addStudent(student, exam, grade, olympiad
                        , extparam
                );
                model.addAttribute("addedStudent",
                        "Student został dodany!");
                return "/student/student";
            } else {
                model.addAttribute("emailExist", "Taki email już istnieje");
                return "/student/student";
            }
        }
    }


    @GetMapping("/showStudents")
    public String back(Model model, Principal principal) {
        model.addAttribute("allStudents", studentService.getAllStudents());
        return "/thanksForSignIn";
    }

    @GetMapping("/aboutStudent/{id}")
    public String showMoreAboutStudent(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student student = studentService.findUserById(id).orElse(null);
            Exam exam = studentService.findExamById(id).orElse(null);
            Grade grade = studentService.findGradeById(id).orElse(null);
            Olympiad olympiad = studentService.findOlympiadById(id).orElse(null);
            ExtraParameters extrParam = studentService.findExParamById(id).orElse(null);
            model.addAttribute("student", student);
            model.addAttribute("exam", exam );
            model.addAttribute("grade", grade);
            model.addAttribute("olympiad", olympiad);
            model.addAttribute("extrParam", extrParam);

            return "/student/moreAboutStudent";
        }
    }

}
