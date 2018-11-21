package com.sda.projectd;

import com.sda.projectd.model.Address;
import com.sda.projectd.model.Company;
import com.sda.projectd.service.CompanyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
        company.setNewName("Company S.A.");
        company.setAddress(new Address("Kwiatowa", "1", "2", "00-111", "Miasto"));
        company.setKrs("1111111111");
        company.setNip("111111111");
        company.setRegon("111111111");
        // when
        mockMvc.perform(post("/add").param("newName", "Company S.A.")
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

   /* @DisplayName("should load all companies")
    @Test
    void test2() throws Exception {
        // given
        Company fakeCompany = new Company();
        when(companyService.findAll()).thenReturn(Arrays.asList(fakeCompany));

        // mock mvc...

        // then
        .andExpect(model().attribute("company", fakeCompany))
    }*/
}
