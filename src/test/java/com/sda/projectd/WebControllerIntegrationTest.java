package com.sda.projectd;

import com.sda.projectd.model.Address;
import com.sda.projectd.model.Company;
import com.sda.projectd.service.CompanyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@WebMvcTest
public class WebControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @DisplayName("should add new Company when POST is called on /add")
    @Test
    void test1() throws Exception {
        Company company = new Company();
        company.setCurrentName("Company S.A.");
        company.setAddress(new Address("Kwiatowa", "1", "2", "00-111", "Miasto"));
        company.setKrs("1111111111");
        company.setNip("111111111");
        company.setRegon("111111111");
        // when
        mockMvc.perform(post("/add").param("currentName", "Company S.A.")
                .param("address.street", "Kwiatowa")
                .param("address.houseNumber", "1")
                .param("address.flatNumber", "2")
                .param("address.postalCode", "00-111")
                .param("address.city", "Miasto")
                .param("krs", "1111111111")
                .param("nip", "111111111")
                .param("regon", "111111111"))
                //then
                .andExpect(status().isOk());
        verify(companyService, times(1)).addCompany(company);
    }

    @DisplayName("should load findBy name companies")
    @Test
    void test2() throws Exception {
        // given
        Company fakeCompany = new Company();
        fakeCompany.setCurrentName("C S.A.");
        fakeCompany.setAddress(new Address("Uliczna", "2", "1", "11-111", "Miasto"));
        fakeCompany.setKrs("11111111111");
        fakeCompany.setNip("222222222");
        fakeCompany.setRegon("333333333");
        Collection<Company> expectedCompanies = Arrays.asList(fakeCompany);
        when(companyService.findByName("C S.A.")).thenReturn(expectedCompanies);

        // mock mvc...
        mockMvc.perform(get("/companies").param("nameToFind", "C S.A."))
                // then
                .andExpect(status().isOk())
                .andExpect(model().attribute("findCompanies", expectedCompanies));
        verify(companyService, times(1)).findByName("C S.A.");
    }

    @DisplayName("should change currentName and save old name in names")
    @Test
    void test3() throws Exception {
        Company companyToChange = new Company();
        companyToChange.setCurrentName("Name to change");
        companyToChange.setRegon("1");
        companyToChange.setNip("1");
        companyToChange.setKrs("1");
        companyToChange.setAddress(new Address("w","w","q","q","e"));
        companyToChange.setCurrentName("Changed Name");
        Collection<Company>toReturn= Arrays.asList(companyToChange);
       when(companyService.findByName("Name to change")).thenReturn(toReturn);


//
//        Long id = company.getId();
//        String value=id.toString();
     mockMvc.perform(get("/companies"))
             .andExpect(status().isOk());
     mockMvc.perform(get("/companies").param("nameToFind","Name to change"))
             .andExpect((model()
                     .attribute("findCompanies",companyToChange)));

//                .andExpect(model().attribute("company.names",companyToChange.getNames()));
    }
}
