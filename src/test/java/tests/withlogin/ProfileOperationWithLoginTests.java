package tests.withlogin;

import helpers.WithLogin;
import helpers.AuthContext;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static data.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static specs.BookStoreSpecs.*;

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
                userId = AuthContext.getUserId(),
                expires = AuthContext.getExpires();

        step("Отправить запрос на добавление книги", () ->
                given()
                        .header("Authorization", "Bearer " + token)
                        .body(addBookSpec(userId, BOOK_LIST))
                        .contentType(JSON)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .statusCode(201));

        step("Отправить запрос на удаление книги", () ->
                given()
                        .header("Authorization", "Bearer " + token)
                        .body(deleteBookSpec(GIT_BOOK_ISBN, userId))
                        .contentType(JSON)
                        .when()
                        .delete("/BookStore/v1/Book")
                        .then()
                        .statusCode(204));

        PROFILE_PAGE
                .openProfilePage(userId, expires, token)
                .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }

    @Test
    @WithLogin
    @DisplayName("Успешное удаление товара из списка через UI взаимодействие c @WithLogin")
    void successfulUiDeleteBookFromListTest() {

        String
                token = AuthContext.getToken(),
                userId = AuthContext.getUserId(),
                expires = AuthContext.getExpires();

        step("Добавляем книгу через API", () ->
                given()
                        .header("Authorization", "Bearer " + token)
                        .body(addBookSpec(userId, BOOK_LIST))
                        .contentType(JSON)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .statusCode(201));

        PROFILE_PAGE
                .openProfilePage(userId, expires, token)
                .checkingProfileBeforeDelete(AUTH_DATA.getUserName(), GIT_BOOK_TITLE)
                .deleteBooksInProfile()
                .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }
}
