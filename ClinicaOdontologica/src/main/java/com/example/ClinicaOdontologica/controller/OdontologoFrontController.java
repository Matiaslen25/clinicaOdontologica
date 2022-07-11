package com.example.ClinicaOdontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OdontologoFrontController {
    @GetMapping(path = "/odontologos_index")
    @ResponseBody
    public ModelAndView odontologoIndex() {
        return new ModelAndView("odontologos.html");
    }
}
