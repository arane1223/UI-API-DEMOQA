package tests;

import io.qameta.allure.Owner;
import io.restassured.response.Response;
import models.AddListOfBooksModel;
import models.StringObjectModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static data.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static specs.BookStoreSpecs.baseReqSpec;
import static specs.BookStoreSpecs.baseRespSpec;

@Owner("sergeyglukhov")
@Tag("uiapi")
@DisplayName("UI+API тест на demoqa")
public class ProfileOperationTests extends TestBase {

    @Test
    @DisplayName("Успешное удаление товара из списка через API запрос")
    void successfulApiDeleteBookFromListTest() {
        Response authResponse = step("Отправить запрос на авторизацию", () ->
                given(baseReqSpec)
                        .body(AUTH_DATA)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(baseRespSpec(200))
                        .extract().response());

        String
                token = authResponse.path("token"),
                userId = authResponse.path("userId"),
                expires = authResponse.path("expires");

        step("Отправить запрос на добавление книги «Git Pocket Guide»", () ->
                given(baseReqSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(new AddListOfBooksModel(userId, BOOK_LIST))
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(baseRespSpec(201)));

        step("Отправить запрос на удаление книги «Git Pocket Guide»", () ->
                given(baseReqSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(new StringObjectModel(GIT_BOOK_ISBN, userId))
                        .when()
                        .delete("/BookStore/v1/Book")
                        .then()
                        .spec(baseRespSpec(204)));

                profilePage
                        .cookieAuth(userId, expires, token)
                        .openProfilePage()
                        .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }

    @Test
    @DisplayName("Успешное удаление товара из списка через UI взаимодействие")
    void successfulUiDeleteBookFromListTest() {
        Response authResponse = step("Отправить запрос на авторизацию", () ->
                given(baseReqSpec)
                        .body(AUTH_DATA)
                        .contentType(JSON)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(baseRespSpec(200))
                        .extract().response());

        String
                token = authResponse.path("token"),
                userId = authResponse.path("userId"),
                expires = authResponse.path("expires");

        step("Отправить запрос на добавление книги «Git Pocket Guide»", () ->
                given(baseReqSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(new AddListOfBooksModel(userId, BOOK_LIST))
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(baseRespSpec(201)));

        profilePage
                .cookieAuth(userId, expires, token)
                .openProfilePage()
                .checkingProfileBeforeDelete(AUTH_DATA.getUserName(), GIT_BOOK_TITLE)
                .deleteBooksInProfile()
                .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }
}
