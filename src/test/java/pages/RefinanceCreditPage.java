package pages;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class RefinanceCreditPage {
    private final SelenideElement calculateCredit = $(By.xpath("//*[text()='Калькулятор рефинансирования кредитов']"));
    private final SelenideElement sumCredits = $(By.xpath("//div[@label='Сумма текущих кредитов']/input"));
    private final SelenideElement payment = $(By.xpath("//hundefined[@class='Wrapper-sc-6nwvzq-0 jUoPbu']"));
    private final SelenideElement form = $(By.xpath("//div[@class='GridContainer-sc-1nkonw5-0 styled__PersonalInfoWrapper-sc-fjcc82-1 eYUCpm czNEyG']"));
    private final SelenideElement formName = form.$(By.xpath(".//div[@label='Фамилия, имя и отчество*']//textarea"));
    private final SelenideElement formBirthDate = form.$(By.xpath(".//div[@label='Дата рождения*']//input"));
    private final SelenideElement formPhoneNumber = form.$(By.xpath(".//div[@label='Мобильный телефон*']//input"));
    private final SelenideElement buttonNext = $(By.xpath("//button[.//div[text()='Далее']]"));
    private final SelenideElement acceptTerms = $(By.xpath("//label[.//span[text()='условиями рассмотрения заявки']]"));
    private final SelenideElement acceptCheckBox = acceptTerms.$(By.xpath("./div[@type='checkbox']"));


    public RefinanceCreditPage calculatorCredit() {
        int sum = ThreadLocalRandom.current().nextInt(20000, 5000000);
        calculateCredit.scrollTo();
        sumCredits.sendKeys(Keys.CONTROL + "A");
        sumCredits.sendKeys(Keys.BACK_SPACE);
        sumCredits.setValue(String.valueOf(sum));
        sumCredits.getValue().replace(" ", "").contains(String.valueOf(sum));
        Assertions.assertFalse(payment.getText().replaceAll("\\D+", "").equals("0"));
        return this;
    }

    public RefinanceCreditPage checkForm(String name, String birtDate, String phoneNumber, boolean accept) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        form.scrollTo();
        formName.sendKeys(name);
        formBirthDate.sendKeys(birtDate);
        formPhoneNumber.sendKeys(phoneNumber);
        if (accept){acceptCheckBox.click();}

        buttonNext.click();

        if (formName.getText().isEmpty()) {
            assert form.$(By.xpath("./div[1]")).getText().contains("Введите ФИО полностью");
        } else if (!Pattern.matches(".*\\p{InCyrillic}.*", formName.getText())) {
            assert form.$(By.xpath("./div[1]")).getText().contains("Используйте только кириллицу");
        } else if ((formName.getText().length() - formName.getText().replaceAll(" ", "").length()) == 0) {
            assert form.$(By.xpath("./div[1]")).getText().contains("Введите ФИО полностью");
        }

        if (formBirthDate.getValue().isEmpty() || formBirthDate.getValue().contains("_")) {
            assert form.$(By.xpath("./div[2]")).getText().contains("Обязательное поле");
        } else if (LocalDate.parse(formBirthDate.getValue(), pattern).isAfter(LocalDate.of(LocalDate.now().getYear() - 20, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()))) {
            assert form.$(By.xpath("./div[2]")).getText().contains("Возраст клиента должен быть не менее 20 лет");
        }

        if (formPhoneNumber.getValue().isEmpty()) {
            assert form.$(By.xpath("./div[3]")).getText().contains("Обязательное поле");
        } else if (formPhoneNumber.getValue().length() < 16) {
            assert form.$(By.xpath("./div[3]")).getText().contains("Введите верный номер телефона");
        }

        if (!accept){assert acceptTerms.ancestor(".GridCell-sc-h43w4t-0 bvnDWU").getText().contains("Для подачи заявки необходимо дать согласие на этот пункт");}
        return this;
    }
}
