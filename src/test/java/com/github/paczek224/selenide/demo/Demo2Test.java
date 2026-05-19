package com.github.paczek224.selenide.demo;

import com.github.paczek224.selenide.SelenideApplicationTests;
import com.github.paczek224.selenide.page.DemoPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.paczek224.selenide.assertions.ElementAssertions.assertThat;

@Feature("Forms")
public class Demo2Test extends SelenideApplicationTests {

    @Autowired
    private DemoPage demoPage;

    @Test
    @Story("Fill last name field")
    public void shouldSuccessfullyFillLastName() {
        String expectedText = "Last name";
        demoPage.openPage();
        demoPage.fillLastName(expectedText);
        assertThat(demoPage).elementHasValue(DemoPage::getLastNameInput, expectedText);
    }
}