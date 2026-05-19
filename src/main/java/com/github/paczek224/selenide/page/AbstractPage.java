package com.github.paczek224.selenide.page;

import static com.codeborne.selenide.Selenide.open;

public abstract class AbstractPage {

    public final void openPage() {
        open(getPageUrl());
    }

    protected abstract String getPageUrl();
}
