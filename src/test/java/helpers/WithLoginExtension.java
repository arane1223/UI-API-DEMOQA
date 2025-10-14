package helpers;

import io.restassured.response.Response;
import org.junit.jupiter.api.extension.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static data.TestData.AUTH_DATA;

public class WithLoginExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        if (context.getTestMethod().isPresent() &&
                context.getTestMethod().get().isAnnotationPresent(WithLogin.class)) {

            Response authResponse = given()
                    .body(AUTH_DATA)
                    .contentType(JSON)
                    .when()
                    .post("/Account/v1/Login")
                    .then()
                    .statusCode(200)
                    .extract().response();

            String token = authResponse.path("token");
            String userId = authResponse.path("userId");
            String expires = authResponse.path("expires");

            AuthContext.set(token, userId, expires);
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        AuthContext.clear();
    }
}
