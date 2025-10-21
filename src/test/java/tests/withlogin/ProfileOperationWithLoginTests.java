package tests.withlogin;

import helpers.WithLogin;
import helpers.AuthContext;
import io.qameta.allure.Owner;
import models.AddListOfBooksModel;
import models.StringObjectModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static data.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.BookStoreSpecs.baseReqSpec;
import static specs.BookStoreSpecs.baseRespSpec;

@Owner("sergeyglukhov")
@Tag("uiapi")
@DisplayName("UI+API тест на demoqa")
public class ProfileOperationWithLoginTests extends TestBaseWithLogin {

    @Test
    @WithLogin
    @DisplayName("Успешное удаление товара из списка через API запрос c @WithLogin")
    void successfulApiDeleteBookFromListTest() {
        String
                token = AuthContext.getToken(),
                userId = AuthContext.getUserId();

        step("Отправить запрос на добавление книги", () ->
                given(baseReqSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(new AddListOfBooksModel(userId, BOOK_LIST))
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(baseRespSpec(201)));

        step("Отправить запрос на удаление книги", () ->
                given(baseReqSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(new StringObjectModel(GIT_BOOK_ISBN, userId))
                        .when()
                        .delete("/BookStore/v1/Book")
                        .then()
                        .spec(baseRespSpec(204)));

        profilePage
                .openProfilePage()
                .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }

    @Test
    @WithLogin
    @DisplayName("Успешное удаление товара из списка через UI взаимодействие c @WithLogin")
    void successfulUiDeleteBookFromListTest() {
        String
                token = AuthContext.getToken(),
                userId = AuthContext.getUserId();

        step("Добавляем книгу через API", () ->
                given(baseReqSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(new AddListOfBooksModel(userId, BOOK_LIST))
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(baseRespSpec(201)));

        profilePage
                .openProfilePage()
                .checkingProfileBeforeDelete(AUTH_DATA.getUserName(), GIT_BOOK_TITLE)
                .deleteBooksInProfile()
                .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }
}
