package com.example.ClinicaOdontologica.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @GetMapping("/index")
    public ModelAndView home(){
        return new ModelAndView("index.html");
    }

    @GetMapping("/index/odontologos")
    public ModelAndView odontologos(){
        return new ModelAndView("odontologos.html");
    }

    @GetMapping("/index/pacientes")
    public ModelAndView pacientes(){
        return new ModelAndView("pacientes.html");
    }

    @GetMapping("/index/turnos")
    public ModelAndView turnos(){
        return new ModelAndView("turnos.html");
    }
}
