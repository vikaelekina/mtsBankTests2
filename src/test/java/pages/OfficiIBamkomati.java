package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class OfficiIBamkomati {
    private final SelenideElement citySelection = $(By.xpath("//input[@placeholder='Выберите город']"));
    //    private final SelenideElement buttonSpiskom = $(By.xpath("//div[@data-testid='tabsbar']//button[2]"));
    private final SelenideElement buttonSpiskom = $(By.xpath("//div[text()='Списком']"));
    private final SelenideElement OpenFilters = $(By.xpath("//div[@class='styled__Container-sc-szata3-0 jhjBtN']"));
    private final SelenideElement filter24Hours = $(By.xpath("//div[text()='Работает круглосуточно']")).ancestor("label");
    private final SelenideElement filterOpenNow = $(By.xpath("//div[text()='Работает сейчас']")).ancestor("label");
    private final ElementsCollection timeWork = $$(By.xpath("//div[text()='Обслуживание физических лиц']"));
    private final SelenideElement butttonNextPage = $$(By.xpath("//nav[@class='Container-sc-13zz2b5-0 tlTGd']/button")).last();


    @Step("Изменить способ отображения объектов на список")
    public OfficiIBamkomati changeDisplayItems() {
        buttonSpiskom.click();
        return this;
    }

    @Step("Проверить отображение имени города в поисковой строке")
    public OfficiIBamkomati checkCityName(String nameCity) {
        citySelection.shouldHave(value(nameCity));
        return this;
    }

    @Step("Выбрать фильтр - работает 24 часа")
    public OfficiIBamkomati chooseFilter24Hour() {
        OpenFilters.click();
        filter24Hours.$(By.xpath(".//div[@type='checkbox']")).scrollTo().click();
        filterOpenNow.$(By.xpath(".//div[@type='checkbox']")).scrollTo().click();
        return this;
    }

    @Step("Проверить, что на каждой карточек отеля отображается - Круглосуточно")
    public OfficiIBamkomati checkFilter24Hour() {
        while (butttonNextPage.attr("disabled") == null) {
            timeWork.stream().forEach(t -> t.ancestor("div").shouldHave(Condition.text("Круглосуточно")));
            butttonNextPage.click();
        }
        timeWork.stream().forEach(t -> t.ancestor("div").shouldHave(Condition.text("Круглосуточно")));
        return this;
    }
}
