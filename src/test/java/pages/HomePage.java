package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    //    private final SelenideElement notElse = $(By.xpath("//button[@class='Wrapper-sc-16137b7a-1 bvkAem sc-dlfnuX iSGgmM']"));
    private final SelenideElement notElse = $(By.xpath("//button[.//div[text()='Нет, другой']]"));
    private final SelenideElement buttonCity = $(By.xpath("//div[@id='top']/div[2]"));
    //    private final SelenideElement officiIBankomayti = $(By.xpath("//a[@href='/ofisi-i-bankomati/']"));
    private final SelenideElement officiIBankomayti = $(By.xpath("//a[text()='Офисы и банкоматы']"));
    private final SelenideElement credits = $(By.xpath("//a[text()='Кредиты']"));
    //    private final SelenideElement refinanceCredits = $(By.xpath("//a[@href='/chastnim-licam/krediti/refinans/']"));
    private final SelenideElement refinanceCredits = $(By.xpath("//a[text()='Рефинансирование кредитов']"));
    private SelenideElement city;


    public HomePage openPage() {
        Selenide.open("https://www.mtsbank.ru/");
        return this;
    }

    @Step("Выбор города")
    public HomePage chooseCity(String nameCity) {
        if (notElse.isDisplayed()) {
            notElse.click();
        } else {
            buttonCity.click();
        }
        city = $(By.xpath("//button[text()='" + nameCity + "']"));
        city.scrollTo().click();
        buttonCity.shouldHave(text(nameCity));
        return this;
    }

    @Step("Открытие страницы офисов и банкоматов")
    public HomePage openOfficiIBankomati() {
        officiIBankomayti.click();
        return this;
    }

    @Step("Открытие страницы рефинансирования кредита")
    public HomePage openCredits() {
        credits.hover();
        refinanceCredits.click();
        return this;
    }

}
