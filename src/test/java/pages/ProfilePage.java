package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProfilePage {
    SelenideElement
            userName = $("#userName-value"),
            bookTable = $(".rt-tbody"),
            deleteBook = $("#delete-record-undefined"),
            ok = $("#closeSmallModal-ok");

    @Step("Авторизоваться через cookie")
    public ProfilePage cookieAuth(String userId, String expires, String token) {
        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));
        return this;
    }

    @Step("Открыть страницу с профилем")
    public ProfilePage openProfilePage() {
        open("/profile");
        return this;
    }

    @Step("Проверить, что открыт правильный профиль и в нем пустая библиотека")
    public ProfilePage checkingProfileAfterDelete(String user) {
        userName.shouldHave(text(user));
        bookTable.shouldBe(empty);
        return this;
    }

    @Step("Проверить, что открыт корректный профиль и в нем есть книга")
    public ProfilePage checkingProfileBeforeDelete(String user, String title) {
        userName.shouldHave(text(user));
        bookTable.shouldBe(text(title));
        return this;
    }

    @Step("Удалить книги из профиля")
    public ProfilePage deleteBooksInProfile() {
        deleteBook.click();
        ok.click();
        return this;
    }
}
