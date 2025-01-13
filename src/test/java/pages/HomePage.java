package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    //не уникальный путь, но хз важно ли, второй такой же по сути
    private final SelenideElement notElse = $(By.xpath("//button[@class='Wrapper-sc-16137b7a-1 bvkAem sc-dlfnuX iSGgmM']"));
    private final SelenideElement buttonCity = $(By.xpath("//div[@id='top']/div[2]"));
    private final SelenideElement officiIBankomayti = $(By.xpath("//a[@href='/ofisi-i-bankomati/']"));
    private final SelenideElement credits = $(By.xpath("//a[@href='/chastnim-licam/krediti/']"));
    private final SelenideElement refinanceCredits = $(By.xpath("//a[@href='/chastnim-licam/krediti/refinans/']"));
//    private final SelenideElement closeChooseCity =$(By.className("LinkWrapper-sc-f1cacf38-0 dDIvqO sc-jSgvzq hZwIzs"))

    private SelenideElement city;

    public HomePage openPage() {
        Selenide.open("https://www.mtsbank.ru/");
        return this;
    }

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

    public HomePage openIfficiIBankomati() {
        officiIBankomayti.click();
        return this;
    }

    public HomePage openCredits(){
        credits.hover();
        refinanceCredits.click();
        return this;
    }

}
