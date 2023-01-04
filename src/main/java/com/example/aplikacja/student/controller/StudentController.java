package com.example.aplikacja.student.controller;

import com.example.aplikacja.appuser.AppUserService;
import com.example.aplikacja.student.dto.*;
import com.example.aplikacja.student.entity.*;
import com.example.aplikacja.student.service.KlassService;
import com.example.aplikacja.student.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class StudentController {

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    private StudentService studentService;
    private AppUserService appUserService;
    private KlassService klassService;

    @GetMapping("/studentForm") //przycisk "Dodaj ucznia na podstronie hello"
    public String addStudent(Model model) {
        model.addAttribute(new StudentDTO());
        model.addAttribute(new ExamDTO());
        return "/student/student";
    }

//    @GetMapping("/addClass")
//    public String addClass(Model model) {
//        model.addAttribute(new KlasaDTO());
//        return "/class/addClass";
//    }

    @PostMapping("/student/student")
    public String add(StudentDTO student, ExamDTO exam,
                      GradeDTO grade, OlympiadDTO olympiad
            , ExtraParametersDTO extparam, KlasaDTO klasa,
                      Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student appUserWithEmail = studentService.findUserByEmail(student.getEmail()).orElse(null);
            if (appUserWithEmail == null) {
                studentService.addStudent(student, exam, grade, olympiad, extparam);
                model.addAttribute("addedStudent",
                        "Student został dodany!");
                return "/student/student";
            } else {
                model.addAttribute("emailExist", "Taki email już istnieje");
                return "/student/student";
            }
        }
    }

//    @PostMapping("/class/addClass")
//    public String add(KlasaDTO klasa, Model model, Principal principal) {
//        if (principal == null) {
//            return "userIsLogout";
//        } else {
//            Klasa classWithSymbol = studentService.findClassBySymbol(klasa.getSymbol()).orElse(null);
//            if (classWithSymbol == null) {
//                studentService.addClass(klasa);
//                model.addAttribute("addedClass",
//                        "Klasa została dodana!");
//                return "/class/addClass";
//            } else {
//                model.addAttribute("emailExist", "Taka klasa już istnieje");
//                return "/class/addClass";
//            }
//        }
//    }


    @GetMapping("/showStudents")
    public String back(Model model) {
        model.addAttribute("allStudents", studentService.getAllStudents());
        return "/thanksForSignIn";
    }

//    @GetMapping("/listOfClass")
//    public String showList(Model model) {
//        model.addAttribute("allClass", studentService.getAllKlass());
//        return "/listOfClass";
//    }


//    @GetMapping("/changeClass")
//    private String changeClass(Model model, Principal principal) {
//        Student student = studentService.findUserByEmail(principal.getName()).orElse(new Student());
//        model.addAttribute("student", student);
//
//        return "/student/changeClass";
//    }

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
            model.addAttribute("exam", exam);
            model.addAttribute("grade", grade);
            model.addAttribute("olympiad", olympiad);
            model.addAttribute("extrParam", extrParam);

            return "/student/moreAboutStudent";
        }
    }

    @GetMapping("/countPoints/{id}")
    public String points(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student student = studentService.findUserById(id).orElse(null);
            Exam exam = studentService.findExamById(id).orElse(null);
//            Grade grade = studentService.findGradeById(id).orElse(null);
//            Olympiad olympiad = studentService.findOlympiadById(id).orElse(null);
//            ExtraParameters extrParam = studentService.findExParamById(id).orElse(null);
//            Klasa klasa = studentService.findClassById(id).orElse(null);
            Student newparam = studentService.pointsOfStudent(student, exam);
            model.addAttribute("student", student);
            model.addAttribute("exam", exam);
//            model.addAttribute("grade", grade);
//            model.addAttribute("olympiad", olympiad);
//            model.addAttribute("extrParam", extrParam);
//            model.addAttribute("klasa", klasa);
            model.addAttribute("student", newparam);

            return "/student/moreAboutStudent";
        }
    }


    @GetMapping("/updateStudent/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student student = studentService.findUserById(id).orElse(null);
            model.addAttribute(student);
            model.addAttribute("student", student);
            return "/student/updateStudent";
        }
    }


    @PutMapping("/changeStudent")
    public String makeUpdate(StudentDTO student, ExamDTO exam, GradeDTO grade, OlympiadDTO olymp,
                             ExtraParametersDTO extraparam, Model model) {
        Student newStudent = studentService.updateStudent(student, exam, grade, olymp, extraparam);
        model.addAttribute("student", newStudent);
        return "/student/updateStudent";
    }

    @GetMapping("/confirmDelete/{id}")
    private String confirm(@PathVariable("id") Long id, Model model, Principal principal) {
        Student student = studentService.findUserByEmail(principal.getName()).orElse(new Student());
        model.addAttribute("student", student.getId());
        model.addAttribute("id", id);

        return "/student/confirmDelete";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student student = studentService.findUserById(id).orElse(null);
            studentService.deleteOurStudent(student);
            model.addAttribute("allStudents", studentService.getAllStudents());
            return "/thanksForSignIn";
        }
    }

//    @GetMapping("/findStudentBySurname")
//    private String findStudentBySurname(Principal principal, Model model, String surname) {
//        if (principal == null) {
//            return "userIsLogout";
//        } else {
//            Student student = studentService.findUserByEmail(principal.getName()).orElse(null);
//            model.addAttribute("students", studentService.getStudentsSurname());
//            model.addAttribute("student", studentService.findStudentBySurname(surname));
//            model.addAttribute("notFindStudent", "Sorry... Student not find try again.");
//            model.addAttribute(student);
//
//            return "/thanksForSignIn";
//        }
//    }
}
