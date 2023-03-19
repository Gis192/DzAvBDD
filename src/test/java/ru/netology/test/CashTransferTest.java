package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CashTransferTest {
    LoginPage loginPage;
    DashboardPage dashboardPage;


    @BeforeEach
    void setup () {
       loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataGenerator.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataGenerator.getVerificationCode();
        dashboardPage = verificationPage.validCode(verificationCode);
    }

    @Test
    void shouldTransferMoneyToSecondCard ()   {
        var firstCardBalance = DataGenerator.getFirstCardBalance();
        var secondCardBalance = DataGenerator.getSecondCardBalance();
        var firstCardInfo = dashboardPage.getCardBalance(firstCardBalance);
        var secondCardInfo = dashboardPage.getCardBalance(secondCardBalance);
        var amount = DataGenerator.generateValidAmount(firstCardInfo);
        var expectedBalanceOfFirstCard = firstCardInfo - amount;
        var expectedBalanceOfSecondCard = secondCardInfo + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardBalance);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardBalance);
        var actualBalanceOfFirstCard = dashboardPage.getCardBalance(firstCardBalance);
        var actualBalanceOfSecondCard = dashboardPage.getCardBalance(secondCardBalance);
        assertEquals(expectedBalanceOfFirstCard, actualBalanceOfFirstCard);
        assertEquals(expectedBalanceOfSecondCard, actualBalanceOfSecondCard);

    }
    @Test
    void shouldTransferMoneyToFirstCard () {
        var firstCardBalance = DataGenerator.getFirstCardBalance();
        var secondCardBalance = DataGenerator.getSecondCardBalance();
        var firstCardInfo = dashboardPage.getCardBalance(firstCardBalance);
        var secondCardInfo = dashboardPage.getCardBalance(secondCardBalance);
        var amount = DataGenerator.generateValidAmount(firstCardInfo);
        var expectedBalanceOfFirstCard = firstCardInfo + amount;
        var expectedBalanceOfSecondCard = secondCardInfo - amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCardBalance);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), secondCardBalance);
        var actualBalanceOfFirstCard = dashboardPage.getCardBalance(firstCardBalance);
        var actualBalanceOfSecondCard = dashboardPage.getCardBalance(secondCardBalance);
        assertEquals(expectedBalanceOfFirstCard, actualBalanceOfFirstCard);
        assertEquals(expectedBalanceOfSecondCard, actualBalanceOfSecondCard);
    }

}

