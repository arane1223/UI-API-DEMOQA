package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.ProfilePage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.screenshot;
import static helpers.CustomAllureListener.withCustomTemplates;

public class TestBase {
    ProfilePage profilePage = new ProfilePage();

    @BeforeAll
    public static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();
    }

    @BeforeEach
    void addAllureListener() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addFilter(withCustomTemplates())
                .build();
    }

    @AfterEach
    void shutDown() {
        closeWebDriver();
    }
}
