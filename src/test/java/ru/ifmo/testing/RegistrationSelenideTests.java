package ru.ifmo.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.testing.report.AllureSelenideExtention;
import ru.ifmo.testing.utils.SelenideTests;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты регистрации (Selenide)")
@ExtendWith(AllureSelenideExtention.class)
public class RegistrationSelenideTests extends SelenideTests {
    @BeforeEach
    public void openPage() {
        regPage.openPage();
        acceptCookies();
    }

    @Test
    @DisplayName("Регистрация существующего пользователя")
    public void testRegistrationWithExistingUser() throws InterruptedException {
        regPage.register("Ternik2705@list.ru", "ValidPass123!", true);

        regPage.waitEmailErrorMessageVisible();
        assertTrue(regPage.getEmailErrorText().contains("Пользователь с таким e-mail уже существует"),
                "Должно отображаться сообщение о существующем пользователе");
    }

    @Test
    @DisplayName("Регистрация с пустыми полями")
    public void testRegistrationWithEmptyFields() {
        regPage.clickRegister();

        regPage.waitEmailErrorMessageVisible();

        String errorText = regPage.getEmailErrorText();
        assertTrue(errorText.contains("укажите адрес почты"),
                "Должно быть сообщение об email");
        regPage.waitForText("Пожалуйста, придумайте пароль");
        regPage.waitForText("Необходимо ваше согласие");
        regPage.waitForText("Подтвердите, что вы не робот");
    }

    @Test
    @DisplayName("Регистрация с несуществующим доменом")
    public void testRegistrationWithInvalidDomain() {
        regPage.register("user@abracadabra.com", "ValidPass123!", true);

        regPage.waitEmailErrorMessageVisible();
        assertTrue(regPage.getEmailErrorText().contains("Домен почтового сервиса не найден"),
                "Должно отображаться сообщение о несуществующем домене");
    }

    @Test
    @DisplayName("Нельзя отправить форму с ненадежным паролем")
    public void testRegistrationWithWeakPassword() {
        // Тест короткого пароля
        regPage.register("test@example.ru", "123", true);
        assertTrue(regPage.isPasswordErrorIconVisible(),
                "Должна отображаться иконка ошибки пароля");
        assertTrue(regPage.getPasswordErrorText().contains("Минимум 8 символов"),
                "Должно быть сообщение о минимальной длине");

        // Тест без заглавных/строчных букв
        regPage.fillPassword("password123");
        regPage.clickRegister();
        assertTrue(regPage.getPasswordErrorText().contains("заглавные и строчные"),
                "Должно быть сообщение о заглавных и строчных буквах");

        // Тест без цифр
        regPage.fillPassword("Password");
        regPage.clickRegister();
        assertTrue(regPage.getPasswordErrorText().contains("1 цифра"),
                "Должно быть сообщение о цифре");

        // Тест без специальных символов
        regPage.fillPassword("Password1");
        regPage.clickRegister();
        assertTrue(regPage.getPasswordErrorText().contains("1 специальный символ"),
                "Должно быть сообщение о специальном символе");
    }
}