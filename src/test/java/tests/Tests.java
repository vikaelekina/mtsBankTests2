package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import pages.HomePage;
import pages.OfficiIBamkomati;
import pages.RefinanceCreditPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class Tests extends BaseTest {
    HomePage homePage = new HomePage();
    OfficiIBamkomati officiIBamkomati = new OfficiIBamkomati();
    RefinanceCreditPage refinanceCreditPage = new RefinanceCreditPage();

    public String city = "Казань";


    @Test
    @DisplayName("Проверка выбора города")
    public void testFilterCity() {
        homePage.openPage().chooseCity(city).openOfficiIBankomati();
        officiIBamkomati.checkCityName(city);
    }

    @Test
    @DisplayName("Проверка фильтра круглосуточной работы на странице 'Офисы и банкоматы'")
    public void testFilterAllHoursWorking() {
        homePage.openPage().chooseCity(city).openOfficiIBankomati();
        officiIBamkomati.changeDisplayItems().chooseFilter24Hour().checkFilter24Hour();
    }

    @Test
    @DisplayName("Проверка калькулятора рефинансирования кредитов")
    public void testRefinanceCalculator() {
        homePage.openPage().openCredits();
        refinanceCreditPage.calculatorCredit();
    }

    @ParameterizedTest(name = "Проверка отправки заявки со значениями {index}: {0}, {1}, {2}, {3}")
    @DisplayName("Проверка отправки заявки на рефинансирование с невалидными значениями")
    @ArgumentsSource(TestRefinanceFormArgumentsProvider.class)
    public void testRefinanceForm(String name, String birthDate, String phoneNumber, boolean accept) {
        homePage.openPage().openCredits();
        refinanceCreditPage.fillForm(name, birthDate, phoneNumber, accept).checkForm(name, birthDate, phoneNumber, accept);
    }

    static class TestRefinanceFormArgumentsProvider implements ArgumentsProvider {
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String normalBirthDate = LocalDate.of(LocalDate.now().getYear() - 21, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()).format(pattern);
            return Stream.of(
                    Arguments.of("","01.01.1999" , "9999999999", true),
                    Arguments.of("Ivanov Ivan", "01.01.1999", "9999999999", true),
//                    Arguments.of("1 2", "01.01.1999", "9999999999", true),
                    Arguments.of("Иванов ", "01.01.1999", "9999999999", true),
                    Arguments.of("Иванов Иван", "", "9999999999", true),
//                    Arguments.of("Иванов Иван", "aaa", "9999999999", true),
                    Arguments.of("Иванов Иван", "13.01", "9999999999", true),
                    Arguments.of("Иванов Иван", LocalDate.of(LocalDate.now().getYear() - 17, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()).format(pattern), "9999999999", true),
                    Arguments.of("Иванов Иван", "01.01.1999", "", true),
//                    Arguments.of("Э В", "01.01.1999", "ффф", true),
                    Arguments.of("Иванов Иван", "01.01.1999", "999999", true),
                    Arguments.of("Иванов Иван", "01.01.1999", "9999999999", false)
            );
        }
    }

}
