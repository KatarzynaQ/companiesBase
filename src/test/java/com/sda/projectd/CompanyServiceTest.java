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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        Company command = createCompanyWithAllProperties();

        // when
        companyService.addCompany(command);

        // then
        assertThat(companyService.findByName(command.getCurrentName()).size()).isEqualTo(1);
    }

    @DisplayName("should load a single company by name")
    @Test
    void test1() throws Exception {
        // given
        String name = "Company S.A.";
        Company companyToFind = createCompanyWithName(name);
        companyService.addCompany(createCompanyWithName("ACME"));
        companyService.addCompany(createCompanyWithName(name));
        companyService.addCompany(companyToFind);
        companyService.addCompany(createCompanyWithName("JetBrains"));

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
        companyService.addCompany(createCompanyWithName(name));
        companyService.addCompany(createCompanyWithName(name));
        companyService.addCompany(createCompanyWithName("different name"));
        //when
        Collection<Company> byName = companyService.findByName(name);
        //then
        assertThat(byName.size()).isEqualTo(2);
    }

    @DisplayName("should actualize size of name's list when new name is added")
    @Test
    void test3() throws Exception {
        Company company = createCompanyWithTwoNames();
        assertThat(company.getNames().size()).isEqualTo(2);
        companyService.addCompany(company);
        //when
        Collection<Company> byName = companyService.findByName("Company sp. z o.o.");
        //than
        assertThat(byName).contains(company);
        List<Company>byNameList=new ArrayList(byName);
        assertThat(byNameList.get(0).getNames().size()).isEqualTo(2);
    }

    @DisplayName("should find by old name")
    @Test
    void test4() throws Exception {
        //given
        Company company = createCompanyWithTwoNames();
        companyService.addCompany(company);
        //when
        Collection<Company> byName = companyService.findByName("Nowa nazwa Firmy");
        assertThat(byName.size()).isEqualTo(1);
    }


    private Company createCompanyWithTwoNames() {
        Company company = createCompanyWithAllProperties();
        company.setCurrentName("Nowa nazwa Firmy");
        return company;
    }
    private Company createCompanyWithAllProperties() {
        Company company = new Company();
        company.setCurrentName("Company sp. z o.o.");
        company.setKrs("1234567891");
        company.setNip("1234567891");
        company.setRegon("1234567891");
        return company;
    }
    private Company createCompanyWithName(String name) {
        Company company = new Company();
        company.setCurrentName(name);
        company.setKrs("1234567891");
        company.setNip("1234567891");
        company.setRegon("1234567891");
        return company;
    }
}