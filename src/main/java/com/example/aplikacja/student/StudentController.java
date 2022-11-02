package com.example.aplikacja.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String add(StudentDTO student, ExamDTO exam, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student appUserWithEmail = studentService.findUserByEmail(student.getEmail()).orElse(null);
            if (appUserWithEmail == null) {
                studentService.addStudent(student,exam);
                model.addAttribute("addedStudent",
                        "Student został dodany, przejdź do następnej zakładki.");
                return "/student/student";
            } else {
                model.addAttribute("emailExist", "Taki email już istnieje");
                return "/student/student";
            }
        }
    }

    @PostMapping("/student/examResult")
    public String addExam(Exam exam, Model model, Principal principal, Student student) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student appUserWithEmail = studentService.findUserByEmail(student.getEmail()).orElse(null);
            if (appUserWithEmail != null) {
                studentService.addResult(exam, student);
                model.addAttribute("addedExamResult", "Wyniki egzaminów zostały dodane.");
                return "/student/student";
            } else {
                model.addAttribute("studentNotExist", "Nie znaleziono takiego studenta.");
                return "/student/student";
            }
        }
    }

    @PostMapping("/student/grade")
    public String addGrade(Grade grade, Student student, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student appUserWithEmail = studentService.findUserByEmail(student.getEmail()).orElse(null);
            if (appUserWithEmail != null) {
                studentService.addGrades(grade, student);
                model.addAttribute("addedGradeResult", "Oceny zostały dodane.");
                return "/student/student";
            } else {
                model.addAttribute("studentNotExist", "Nie znaleziono takiego studenta.");
                return "/student/student";
            }
        }
    }

    @PostMapping("/student/olympiad")
    public String addOlympiad(Olympiad olympiad, Student student, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student appUserWithEmail = studentService.findUserByEmail(student.getEmail()).orElse(null);
            if (appUserWithEmail != null) {
                studentService.addOlympiads(olympiad, student);
                model.addAttribute("addedOlympiadResult", "Olimpiady zostały dodane");
                return "/student/student";
            } else {
                model.addAttribute("studentNotExist", "Nie znaleziono takiego studenta.");
                return "/student/student";
            }
        }
    }

    @PostMapping("/student/extraParameters")
    public String addOtherParam(ExtraParameters extrp, Student student, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student appUserWithEmail = studentService.findUserByEmail(student.getEmail()).orElse(null);
            if (appUserWithEmail != null) {
                studentService.addOtherParams(extrp, student);
                model.addAttribute("addedExtraParameters", "Zdolności zostały dodane");
                return "/student/student";
            } else {
                model.addAttribute("studentNotExist", "Nie znaleziono takiego studenta.");
                return "/student/student";
            }
        }

    }
}
