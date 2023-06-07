package com.example.aplikacja.student.controller;

import com.example.aplikacja.appuser.AppUser;
import com.example.aplikacja.appuser.AppUserService;
import com.example.aplikacja.student.dto.*;
import com.example.aplikacja.student.entity.*;
import com.example.aplikacja.student.service.ClassificationService;
import com.example.aplikacja.student.service.KlassService;
import com.example.aplikacja.student.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StudentController {

    public StudentController(StudentService studentService, KlassService klassService,
                             AppUserService appUserService,
                             ClassificationService classificationService) {
        this.studentService = studentService;
        this.appUserService = appUserService;
        this.klassService = klassService;
        this.classificationService = classificationService;
    }

    private StudentService studentService;
    private AppUserService appUserService;
    private KlassService klassService;
    private ClassificationService classificationService;

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
        AppUser appUser = appUserService.findEmail(principal.getName()).orElse(null);
        if (appUser == null) {
            return "userIsLogout";
        } else {
            Student appUserWithEmail = studentService.findUserByEmail(student.getEmail()).orElse(null);
            if (appUserWithEmail == null) {
                studentService.addStudent(student, exam, grade, olympiad, extparam, appUser);
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
    public String back(Model model) {
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
            Klasa klasa = studentService.findClassById(id).orElse(null);
            model.addAttribute("allClass", klassService.getAllKlass());
            model.addAttribute("student", student);
            model.addAttribute("exam", exam);
            model.addAttribute("grade", grade);
            model.addAttribute("olympiad", olympiad);
            model.addAttribute("extrParam", extrParam);
            model.addAttribute("klasa", klasa);

            return "/student/moreAboutStudent";
        }
    }

    @GetMapping("/allCountPoints/{id}")
    public String allPoints(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student student = studentService.findUserById(id).orElse(null);
            studentService.addPointsMatGeoInf(student);
            studentService.addPointsHuman(student);
            studentService.addPointsBiolChemAng(student);
            studentService.addPointsMatAngNiem(student);
            studentService.addPointsArt(student);
            studentService.addPointsSport(student);
            studentService.addPointsFizChemFran(student);
            model.addAttribute("student", student);
            Map<String, Double> mapa = new HashMap<>();
            model.addAttribute("map", studentService.punkty(student, mapa));
            model.addAttribute("key", mapa.keySet());
            Map<String, Double> mapaOlymp = new HashMap<>();
            model.addAttribute("mapa", studentService.punktyOlymp(student, mapaOlymp));
            model.addAttribute("keys", mapaOlymp.keySet());
            model.addAttribute("allClass", klassService.getAllKlass());
            return "/student/points";
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
        if (student != null) {
            model.addAttribute("student", newStudent);
            model.addAttribute("updateStudent", "Nastąpiła aktualizacja!");
            return "/student/updateStudent";
        } else {
            model.addAttribute("errorStudent", "Wystąpił błąd podczas aktualizacji");
            return "/student/updateStudent";
        }
    }


    //    student.getClassForStudent() != null ||
    //    !student.getClassForStudent().isEmpty() ||
    @GetMapping("/classificationStudent/{id}")
    private String classification(@PathVariable("id") Long id, Model model, Principal principal) {
        Student student = classificationService.findUserById(id).orElse(null);
        if (student.getClassForStudent() != null) {
            model.addAttribute("classExist", "Uczeń został już sklasyfikowany");
            model.addAttribute("student", student);
            return "/student/moreAboutStudent";
        } else {
            classificationService.classification(student);
            model.addAttribute("student", student);
            return "/student/classification";
        }
    }

    @GetMapping("/changeClassForStudent/{id}")
    private String changeClass(@PathVariable("id") Long id, Model model, Principal principal) {
        Student student = studentService.findUserById(id).orElse(null);
        model.addAttribute("student", student);
        return "student/setClassForStudent";
    }

    @GetMapping("/updateClassForStudent/{id}")
    public String updateClassForStudent(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student student = studentService.findUserById(id).orElse(null);
            model.addAttribute("student", student);
            model.addAttribute(new StudentDTO());
            return "/student/setClassForStudent";
        }
    }

    @PutMapping("/changedClassForStudent")
    private String changedClassStudent(StudentDTO studentDTO, Model model) {
        Student student = studentService.findUserByEmail(studentDTO.getEmail()).orElse(null);
        model.addAttribute("student", studentService.updateStudentClass(student, studentDTO.getClassForStudent()));
        return "student/classification";
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

    @GetMapping("/makeEditClass/{id}")
    public String editClass(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Student student = studentService.findUserById(id).orElse(null);
            model.addAttribute(student);
            model.addAttribute("student", student);
            model.addAttribute("id", id);
            return "/student/editClass";
        }
    }

    @PutMapping("/editClass")
    public String makeUpdate(Student student, Model model) {
        Student newStudent = studentService.editClassForStudent(student);
        if (student != null) {
            model.addAttribute("student", newStudent);
            model.addAttribute("updateStudent", "Nastąpiła aktualizacja!");
            return "/student/editClass";
        } else {
            model.addAttribute("errorStudent", "Wystąpił błąd podczas aktualizacji");
            return "/student/editClass";
        }
    }

}

