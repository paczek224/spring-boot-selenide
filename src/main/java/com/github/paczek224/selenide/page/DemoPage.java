package com.github.paczek224.selenide.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$;

@Component
@Getter
public class DemoPage extends AbstractPage {

    private final SelenideElement firstNameInput;
    private final SelenideElement lastNameInput;

    public DemoPage() {
        super();
        firstNameInput = $("input#fname").as("First name input");
        lastNameInput = $("input#lname").as("Last name input");
    }

    @Override
    protected String getPageUrl() {
        return "/html/html_forms.asp";
    }

    public void fillFirstName(String value) {
        firstNameInput.setValue(value);
    }

    public void fillLastName(String value) {
        lastNameInput.setValue(value);
    }
}
