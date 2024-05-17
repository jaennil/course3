package ru.dubrovskih.course.third.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.dubrovskih.course.CustomTestWatcher;
import ru.dubrovskih.course.third.BaseTests;

public class YandexMarketTests extends BaseTests {

    @ExtendWith(CustomTestWatcher.class)
    @DisplayName("https://market.yandex.ru basic test")
    @Test
    public void basicTests() {

        pageManager.getHomePage()
                .open()
                .clickCatalogButton()
                .hoverLeftCatalogItem("Ноутбуки и компьютеры")
                .findCategory("Ноутбуки и планшеты")
                .findSubcategory();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}