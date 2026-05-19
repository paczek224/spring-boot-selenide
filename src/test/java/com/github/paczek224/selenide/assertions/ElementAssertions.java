package com.github.paczek224.selenide.assertions;

import com.codeborne.selenide.SelenideElement;
import com.github.paczek224.selenide.page.AbstractPage;

import java.util.function.Function;

import static com.codeborne.selenide.Condition.value;

public class ElementAssertions<V extends AbstractPage> {

    private final V page;

    private ElementAssertions(V page) {
        this.page = page;
    }

    public static <V extends AbstractPage> ElementAssertions<V> assertThat(V page) {
        return new ElementAssertions<V>(page);
    }

    public ElementAssertions<V> elementHasValue(Function<V, SelenideElement> getter, String expected) {
        getter.apply(page).shouldHave(value(expected));
        return this;
    }
}
