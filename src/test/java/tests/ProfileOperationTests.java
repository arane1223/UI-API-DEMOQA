package tests;

import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static data.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static specs.BookStoreSpecs.addBookSpec;
import static specs.BookStoreSpecs.deleteBookSpec;

@Owner("sergeyglukhov")
@DisplayName("UI+API тест на demoqa")
public class ProfileOperationTests extends TestBase {

    @Test
    @DisplayName("Успешное удаление товара из списка через API запрос")
    void successfulApiDeleteBookFromListTest() {
        Response authResponse = step("Отправить запрос на авторизацию", () ->
                given()
                        .body(AUTH_DATA)
                        .contentType(JSON)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .statusCode(200)
                        .extract().response());

        String
                token = authResponse.path("token"),
                userId = authResponse.path("userId"),
                expires = authResponse.path("expires");

        step("Отправить запрос на добавление книги «Git Pocket Guide»", () ->
                given()
                        .header("Authorization", "Bearer " + token)
                        .body(addBookSpec(userId, BOOK_LIST))
                        .contentType(JSON)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .statusCode(201));

        step("Отправить запрос на удаление книги «Git Pocket Guide»", () ->
                given()
                        .header("Authorization", "Bearer " + token)
                        .body(deleteBookSpec(GIT_BOOK_ISBN, userId))
                        .contentType(JSON)
                        .when()
                        .delete("/BookStore/v1/Book")
                        .then()
                        .statusCode(204));

                profilePage
                        .openProfilePage(userId, expires, token)
                        .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }

    @Test
    @DisplayName("Успешное удаление товара из списка через UI взаимодействие")
    void successfulUiDeleteBookFromListTest() {
        Response authResponse = step("Отправить запрос на авторизацию", () ->
                given()
                        .body(AUTH_DATA)
                        .contentType(JSON)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .statusCode(200)
                        .extract().response());

        String
                token = authResponse.path("token"),
                userId = authResponse.path("userId"),
                expires = authResponse.path("expires");

        step("Отправить запрос на добавление книги «Git Pocket Guide»", () ->
                given()
                        .header("Authorization", "Bearer " + token)
                        .body(addBookSpec(userId, BOOK_LIST))
                        .contentType(JSON)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .statusCode(201));

        profilePage
                .openProfilePage(userId, expires, token)
                .checkingProfileBeforeDelete(AUTH_DATA.getUserName(), GIT_BOOK_TITLE)
                .deleteBooksInProfile()
                .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }
}
