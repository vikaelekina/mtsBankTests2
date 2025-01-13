package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
    }

    @AfterEach
    public void turnDown() {
        Selenide.closeWebDriver();
    }
}
