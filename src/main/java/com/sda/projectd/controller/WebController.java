package com.sda.projectd.controller;

import com.sda.projectd.model.Company;
import com.sda.projectd.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class WebController {
    @Autowired
    CompanyService companyService;

    @GetMapping(value = "/add")
    ModelAndView getAddForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add");
        modelAndView.addObject("company", new Company());
        return modelAndView;
    }

    @PostMapping(value = "/add")
    void addCompany(@ModelAttribute("company") Company company) {
        companyService.addCompany(company);
        System.out.println(company.toString());
    }
    @GetMapping(value = "/companies")
    ModelAndView getCompanies(@RequestParam (name = "nameToFind") String name){
        ModelAndView modelAndView = new ModelAndView();
        Collection<Company> companies = companyService.findByName(name);
        modelAndView.addObject("findCompanies",companies);
        modelAndView.setViewName("companies");
        return modelAndView;
    }




}
