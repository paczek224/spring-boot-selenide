package com.github.paczek224.selenide.demo;

import com.github.paczek224.selenide.SelenideApplicationTests;
import com.github.paczek224.selenide.page.DemoPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.paczek224.selenide.assertions.ElementAssertions.assertThat;

@Feature("Forms")
public class Demo1Test extends SelenideApplicationTests {

    @Autowired
    private DemoPage demoPage;

    @Test
    @Story("Fill first name field")
    public void shouldSuccessfullyFillFirstName() {
        String expectedText = "First name";
        demoPage.openPage();
        demoPage.fillFirstName(expectedText);
        assertThat(demoPage).elementHasValue(DemoPage::getFirstNameInput, expectedText);
    }
}