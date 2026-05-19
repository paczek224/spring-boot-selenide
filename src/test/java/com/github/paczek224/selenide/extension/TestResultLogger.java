package com.github.paczek224.selenide.extension;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

@Slf4j
public class TestResultLogger implements TestWatcher {

    private final static String SEPARATOR = "------------------------------------------------";

    @Override
    public void testSuccessful(@NonNull ExtensionContext context) {
        TestWatcher.super.testSuccessful(context);
        log.info("{}:{} PASSED " + SEPARATOR, context.getTestClass().map(Class::getSimpleName).orElse(""), context.getDisplayName());
    }

    @Override
    public void testFailed(@NonNull ExtensionContext context, @Nullable Throwable cause) {
        TestWatcher.super.testFailed(context, cause);
        log.info("{}:{} FAILED " + SEPARATOR, context.getTestClass().map(Class::getSimpleName).orElse(""), context.getDisplayName());
    }
}
