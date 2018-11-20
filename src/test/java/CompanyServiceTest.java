import com.sda.projectd.model.Company;
import com.sda.projectd.service.CompanyService;
import com.sda.projectd.service.CompanyServiceInMemoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyServiceTest {
    private CompanyService companyService;

    @BeforeEach
    public void beforeEach() {
        this.companyService = new CompanyServiceInMemoryImpl();
    }

    @DisplayName("should add a new company with all properties")
    @Test
    void test() throws Exception {
        // given
        Company command = createCommandWithAllProperties();

        // when
        companyService.addCompany(command);

        // then
        Collection<Company> allCompanies = companyService.findAll();
        assertThat(allCompanies).hasSize(1);
        Company addedCompany = allCompanies.iterator().next();
        assertThat(addedCompany.getNames()).contains(command.getNewName());
        assertThat(addedCompany.getKrs()).contains(command.getKrs());
        assertThat(addedCompany.getNip()).contains(command.getNip());
        assertThat(addedCompany.getRegon()).contains(command.getRegon());
    }

    @DisplayName("should throw exception when create a company with the same name as already existing")
    @Test
    void test1() throws Exception {
    }

    @DisplayName("should load all created companies")
    @Test
    void test2() throws Exception {
        // given
        // TODO: add some companies
        Company company1 = createCommandWithAllProperties();
        Company company2 = createCommandWithAllProperties();
        company2.addName("next name");
        Company company3 = createCommandWithAllProperties();
        company3.addName("next name2");

        companyService.addCompany(company1);
        companyService.addCompany(company2);
        companyService.addCompany(company3);
        // when
        Collection<Company> companies = companyService.findAll();

        // then
        assertThat(companies).containsOnly(company1, company2,company3);
    }


    private Company createCommandWithAllProperties() {
        Company company = new Company();
        company.setNames(new HashSet<String>());
        company.addName("Company sp. z o.o.");
        company.setKrs("1234567891");
        company.setNip("1234567891");
        company.setRegon("1234567891");
        return company;
    }
}