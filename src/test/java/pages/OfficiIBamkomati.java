package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.*;

public class OfficiIBamkomati {
    private final SelenideElement citySelection = $(By.xpath("//input[@placeholder='Выберите город']"));
    private final SelenideElement buttonSpiskom = $(By.xpath("//div[@data-testid='tabsbar']//button[2]"));
    private final SelenideElement OpenFilters = $(By.xpath("//div[@class='styled__Container-sc-szata3-0 jhjBtN']"));
    private final SelenideElement filter24Hours = $(By.xpath("//div[text()='Работает круглосуточно']")).ancestor("label");
    private final SelenideElement filterOpenNow = $(By.xpath("//div[text()='Работает сейчас']")).ancestor("label");
    private final ElementsCollection timeWork = $$(By.xpath("//div[text()='Обслуживание физических лиц']"));
    private final SelenideElement butttonNextPage = $$(By.xpath("//nav[@class='Container-sc-13zz2b5-0 tlTGd']/button")).last();


    public OfficiIBamkomati changeDisplayItems() {
        buttonSpiskom.click();
        return this;
    }

    public OfficiIBamkomati checkCityName(String nameCity) {
        citySelection.shouldHave(value(nameCity));
        return this;
    }

    public OfficiIBamkomati chooseFilter24Hour() {
        OpenFilters.click();
        filter24Hours.$(By.xpath(".//div[@type='checkbox']")).scrollTo().click();
        filterOpenNow.$(By.xpath(".//div[@type='checkbox']")).scrollTo().click();
        return this;
    }

    public OfficiIBamkomati checkFilter24Hour(){
        while (butttonNextPage.attr("disabled")==null){
            timeWork.stream().forEach(t->t.ancestor("div").shouldHave(Condition.text("Круглосуточно")));
            butttonNextPage.click();
        }
        timeWork.stream().forEach(t->t.ancestor("div").shouldHave(Condition.text("Круглосуточно")));
        return this;
    }
}
