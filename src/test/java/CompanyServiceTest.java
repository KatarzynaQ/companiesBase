import com.sda.projectd.model.Company;
import com.sda.projectd.service.CompanyService;
import com.sda.projectd.service.CompanyServiceInMemoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyServiceTest {
    private CompanyService companyService;

    @BeforeEach
    public void beforeEach() {
        this.companyService = new CompanyServiceInMemoryImpl();
    }

    @DisplayName("should add a new company with all properties")
    @Test
    void test0() throws Exception {
        // given
        Company command = createCommandWithAllProperties();

        // when
        companyService.addCompany(command);

        // then

        assertThat(companyService.findByName(command.getNewName()).count()).isEqualTo(1L);
    }

    @DisplayName("should load a single company by name")
    @Test
    void test1() throws Exception {
        // given
        String name = "Company S.A.";
        companyService.addCompany(createCommandWithName("ACME"));
        companyService.addCompany(createCommandWithName(name));
        companyService.addCompany(createCommandWithName("JetBrains"));

        // when
        Company foundCompany = companyService.findByName(name).findAny().get();

        //then
        assertThat(foundCompany.getNames()).contains(name);
    }

    @DisplayName("should load two companies with the same name by name")
    @Test
    void test() throws Exception {
        //given
        String name= "Company";
        companyService.addCompany(createCommandWithName(name));
        companyService.addCompany(createCommandWithName(name));
        companyService.addCompany(createCommandWithName("different name"));
        //when
        List<Company> byName = companyService.findByName(name).collect(Collectors.toList());
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