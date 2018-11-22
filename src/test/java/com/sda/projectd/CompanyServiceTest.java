package com.sda.projectd;

import com.sda.projectd.model.Company;
import com.sda.projectd.repository.CompanyRepository;
import com.sda.projectd.service.CompanyService;
import com.sda.projectd.service.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringJUnitConfig
public class CompanyServiceTest {

    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void beforeEach() {
        //this.companyService = new CompanyServiceInMemoryImpl();
        this.companyService = new CompanyServiceImpl(companyRepository);
    }

    @DisplayName("should add a new company with all properties")
    @Test
    void test0() throws Exception {
        // given
        Company command = createCommandWithAllProperties();

        // when
        companyService.addCompany(command);

        // then
        assertThat(companyService.findByName(command.getNewName()).size()).isEqualTo(1);
    }

    @DisplayName("should load a single company by name")
    @Test
    void test1() throws Exception {
        // given
        String name = "Company S.A.";
        Company companyToFind = createCommandWithName(name);
        companyService.addCompany(createCommandWithName("ACME"));
        companyService.addCompany(createCommandWithName(name));
        companyService.addCompany(companyToFind);
        companyService.addCompany(createCommandWithName("JetBrains"));

        // when
        Collection<Company> foundCompany = companyService.findByName(name);

        //then
        assertThat(foundCompany.size()).isEqualTo(2);
    }

    @DisplayName("should load two companies with the same name by name")
    @Test
    void test2() throws Exception {
        //given
        String name = "Company";
        companyService.addCompany(createCommandWithName(name));
        companyService.addCompany(createCommandWithName(name));
        companyService.addCompany(createCommandWithName("different name"));
        //when
        Collection<Company> byName = companyService.findByName(name);
        //then
        assertThat(byName.size()).isEqualTo(2);

    }

    private Company createCommandWithAllProperties() {
        Company company = new Company();
        company.setNewName("Company sp. z o.o.");
        company.setKrs("1234567891");
        company.setNip("1234567891");
        company.setRegon("1234567891");
        return company;
    }

    private Company createCommandWithName(String name) {
        Company company = new Company();
        company.setNewName(name);
        company.setKrs("1234567891");
        company.setNip("1234567891");
        company.setRegon("1234567891");
        return company;
    }
}