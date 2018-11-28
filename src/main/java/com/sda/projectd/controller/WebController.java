package com.sda.projectd.controller;

import com.sda.projectd.model.Company;
import com.sda.projectd.service.CompanyAlreadyExistsException;
import com.sda.projectd.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
public class WebController {
    @Autowired
    CompanyService companyService;

    @GetMapping(value = "/add")
    ModelAndView getAddForm(@RequestParam(value = "companyId", required = false) Long companyId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add");
        Company company = new Company();
        if (companyId != null) {
            company = companyService.findById(companyId).orElse(company);
        }
        modelAndView.addObject("company",
                company);
        return modelAndView;
    }

    @PostMapping(value = "/add")
    void addCompany(@ModelAttribute("company") Company company) {
        if (company.getId() == null)
            companyService.addCompany(company);
        else
            companyService.updateCompany(company.getId(), company);
    }

    @GetMapping(value = "/companies")
    ModelAndView getCompanies(@RequestParam(name = "nameToFind", required = false) String name) {
        ModelAndView modelAndView = new ModelAndView();
        Collection<Company> companies = companyService.findByName(name);
        modelAndView.addObject("findCompanies", companies);
        modelAndView.setViewName("companies");
        return modelAndView;
    }
}
