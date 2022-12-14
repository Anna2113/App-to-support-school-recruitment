package com.example.aplikacja.appuser;


import com.example.aplikacja.student.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {
    private AppUserService appUserService;
    private StudentService studentService;


    public UserController(AppUserService appUserService, StudentService studentService) {
        this.appUserService = appUserService;
        this.studentService = studentService;

    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/registerForm")
    public String register(Model model) {
        model.addAttribute("newUser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String register(AppUser appUser, Model model) {
        AppUser appUserWithEmail = appUserService.findUserByEmail(appUser.getEmail()).orElse(null);
        if (appUserWithEmail == null) {
            appUserService.signUpUser(appUser);
            return "thanksForRegister";
        } else {
            model.addAttribute("emailExist", "Taki email już istnieje");
            return "register";
        }
    }

    @GetMapping("/hello")
    public String hello(Model model, Principal principal) {
        if (principal == null) {
            return "userIsLogout";
        } else {
            AppUser appUser = appUserService.findUserByEmail(principal.getName()).orElse(null);
            model.addAttribute(appUser);
            model.addAttribute("allStudents", studentService.getAllStudents());
            return "thanksForSignIn";
        }
    }

}
