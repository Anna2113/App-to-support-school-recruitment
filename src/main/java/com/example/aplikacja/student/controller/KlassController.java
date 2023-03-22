package com.example.aplikacja.student.controller;

import com.example.aplikacja.student.dto.*;
import com.example.aplikacja.student.entity.*;
import com.example.aplikacja.student.service.KlassService;
import com.example.aplikacja.student.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class KlassController {

    private KlassService klassService;
    private StudentService studentService;

    public KlassController(KlassService klassService, StudentService studentService) {
        this.klassService = klassService;
        this.studentService = studentService;
    }

    @GetMapping("/addClass")
    public String addClass(Model model) {
        model.addAttribute(new KlasaDTO());
        return "/class/addClass";
    }

    @PostMapping("/class/addClass")
    public String add(KlasaDTO klasa, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Klasa classWithSymbol = klassService.findClassBySymbol(klasa.getSymbol()).orElse(null);
            if (classWithSymbol == null) {
                klassService.addClass(klasa);
                model.addAttribute("addedClass",
                        "Klasa została dodana!");
                return "/class/addClass";
            } else {
                model.addAttribute("emailExist", "Taka klasa już istnieje");
                return "/class/addClass";
            }
        }
    }

    @GetMapping("/listOfClass")
    public String showList(Model model) {
        model.addAttribute("allClass", klassService.getAllKlass());
        return "/listOfClass";
    }

    @GetMapping("/reserveList")
    public String showReserveLis(Model model) {
        List<Student> students = studentService.listaRezerwowa();
        Collections.reverse(students);
        model.addAttribute("allStudents", students);
        return "/class/reserveList";
    }


    @GetMapping("/listOfStudentForClass/{id}")
    public String showListOfStudentForClass(@PathVariable("id") Long id, Model model, Principal principal) {
        Klasa klasa = klassService.findClassById(id).orElse(null);

        List<Student> profileClass = studentService.listaStWKl(klasa.getNameOfClass().getLabel());
        Collections.reverse(profileClass);
        model.addAttribute("allStudents", profileClass);
        model.addAttribute("klasa", klasa);
        return "/class/listOfStudentsForClass";
    }


    @GetMapping("/listOfClassForStudents/{id}")
    public String showListForStudent(@PathVariable("id") Long id, Model model, Principal principal) {
        Klasa klasa = klassService.findClassById(id).orElse(null);
        Student student = klassService.findStudentById(id).orElse(null);
        model.addAttribute("allClass", klassService.getAllKlass());
        model.addAttribute("klasa", klasa);
        model.addAttribute("student", student);
        return "/student/points";
    }

//    @GetMapping("/moreAboutClass")
//    public String showClass(Klasa klasa, Model model, Principal principal) {
//        Klasa kl = klassService.findClassBySymbol(principal.getName()).orElse(null);
//        model.addAttribute(klasa);
//        model.addAttribute("klasa", kl);
//        return "/class/moreAboutClass";
//    }

    @GetMapping("/changeClass")
    private String changeClass(Model model, Principal principal) {
        Student student = klassService.findUserByEmail(principal.getName()).orElse(new Student());
        model.addAttribute("student", student);

        return "/student/points";
    }

    @GetMapping("/aboutClass/{id}")
    public String showMoreAboutStudent(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Klasa klasa = klassService.findClassById(id).orElse(null);
            model.addAttribute("klasa", klasa);

            return "/class/moreAboutClass";
        }
    }

    @GetMapping("/countAvg/{id}")
    public String points(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Klasa klasa = klassService.findClassById(id).orElse(null);
            klassService.countParameters(klasa);
            model.addAttribute("klasa", klasa);

            return "/class/moreAboutClass";
        }
    }

    @GetMapping("/countMinAvg/{id}")
    public String averageKier(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Klasa klasa = klassService.findClassById(id).orElse(null);
            klassService.minSrKier(klasa, klasa.getWeightOfGrade());
            model.addAttribute("klasa", klasa);

            return "/class/moreAboutClass";
        }
    }


    @GetMapping("/updateClass/{id}")
    public String updateClass(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Klasa klasa = klassService.findClassById(id).orElse(null);
            model.addAttribute("klasa", klasa);
            model.addAttribute(new KlasaDTO());
            model.addAttribute(new WeightOfGradeDTO());
            return "/class/updateClass";
        }
    }

    @PutMapping("/changeClass")
    public String makeUpdateClass(KlasaDTO klasa, Model model, WeightOfGradeDTO wog) {
        Klasa newClass = klassService.updateKlasa(klasa);
        Klasa newClassSubject = klassService.updateSubjectOfKlass(klasa, wog);
        Klasa newClassSkills = klassService.updateSkillsOfKlass(klasa);
        Klasa newParams = klassService.addNewParameters(klasa);
        if (klasa != null) {
            model.addAttribute("klasa", newClass);
            model.addAttribute("subject", newClassSubject);
            model.addAttribute("skills", newClassSkills);
            model.addAttribute("params", newParams);
            model.addAttribute("updateClass",
                    "Nastąpiła aktualizacja!");
            return "/class/updateClass";
        } else {
            model.addAttribute("errorClass",
                    "Wystąpił błąd podczas aktualizacji");
            return "/class/updateClass";
        }
    }

    @GetMapping("/confirmDeleteClass/{id}")
    private String confirm(@PathVariable("id") Long id, Model model, Principal principal) {
        Klasa klasa = klassService.findClassBySymbol(principal.getName()).orElse(new Klasa());
        model.addAttribute("klasa", klasa.getId());
        model.addAttribute("id", id);

        return "/class/confirmDeleteClass";
    }

    @GetMapping("/deleteClass/{id}")
    public String deleteClass(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Klasa klasa = klassService.findClassById(id).orElse(null);
            klassService.deleteOurClass(klasa);
            model.addAttribute("allClass", klassService.getAllKlass());
            return "/listOfClass";
        }
    }


    @GetMapping("/enterParameters/{id}")
    public String parameters(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            Klasa klasa = klassService.findClassById(id).orElse(null);
            model.addAttribute(klasa);
            model.addAttribute("klasa", klasa);
            return "/class/parametersOfClass";
        }
    }

    @PutMapping("/addParameters")
    public String addNewParam(KlasaDTO klasa, Model model) {
        Klasa newParams = klassService.addNewParameters(klasa);
        if (klasa != null) {
            model.addAttribute("params", newParams);
            model.addAttribute("updateClass",
                    "Nastąpiła aktualizacja!");
            return "/class/parametersOfClass";
        } else {
            model.addAttribute("errorClass",
                    "Wystąpił błąd podczas aktualizacji");
            return "/class/parametersOfClass";
        }
    }

}
