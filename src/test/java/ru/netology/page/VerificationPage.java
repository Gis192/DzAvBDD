package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
        private SelenideElement codeFiled = $("[data-test-id=code] input");
        private SelenideElement acceptFiled = $("[data-test-id=action-verify]");

        public DashboardPage validCode (DataGenerator.VerificationCode info){
            codeFiled.setValue(info.getCode());
            acceptFiled.click();
            return new DashboardPage();
        }
}
