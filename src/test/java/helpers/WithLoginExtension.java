package helpers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static data.TestData.AUTH_DATA;
import static specs.BookStoreSpecs.baseReqSpec;
import static specs.BookStoreSpecs.baseRespSpec;

public class WithLoginExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    @Step("Залогиниться в Book Store")
    public void beforeTestExecution(ExtensionContext context) {
        if (context.getTestMethod().isPresent() &&
                context.getTestMethod().get().isAnnotationPresent(WithLogin.class)) {

            Response authResponse = given(baseReqSpec)
                    .body(AUTH_DATA)
                    .when()
                    .post("/Account/v1/Login")
                    .then()
                    .spec(baseRespSpec(200))
                    .extract().response();

            String token = authResponse.path("token");
            String userId = authResponse.path("userId");
            String expires = authResponse.path("expires");

            AuthContext.set(token, userId, expires);

            open("/images/Toolsqa.jpg");
            getWebDriver().manage().addCookie(new Cookie("userID", userId));
            getWebDriver().manage().addCookie(new Cookie("expires", expires));
            getWebDriver().manage().addCookie(new Cookie("token", token));
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        AuthContext.clear();
    }
}
