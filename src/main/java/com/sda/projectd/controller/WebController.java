package com.sda.projectd.controller;

import com.sda.projectd.model.Company;
import com.sda.projectd.service.CompanyAlreadyExistsException;
import com.sda.projectd.service.CompanyService;
import com.sda.projectd.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

@Controller
public class WebController {
    private CompanyService companyService;
    private FileService fileService;

    public WebController(CompanyService companyService, FileService fileService) {
        this.companyService = companyService;
        this.fileService = fileService;
    }

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

    @GetMapping(value = "/files", produces = "application/octet-stream")
    @ResponseBody
    Resource dowloadFile(@RequestParam("id") String fileId) throws Exception {
        InputStream fileInputStream = fileService.downloadFile(fileId);
        return new InputStreamResource(fileInputStream);
    }

    @PostMapping(value = "/add")
    void addCompany(@ModelAttribute("company") Company company, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (company.getId() == null) {
            if (file == null)
                companyService.addCompany(company);
            else
                companyService.addCompany(company, file.getInputStream());
        } else
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
