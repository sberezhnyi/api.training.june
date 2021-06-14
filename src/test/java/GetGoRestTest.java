import groovy.util.logging.Slf4j;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpHeaders;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static util.PropertiesLoader.API_PATH;
import static util.PropertiesLoader.HOST;
import static util.PropertiesLoader.TOKEN;

@Slf4j
public class GetGoRestTest {

    private String endPoint = "/users";

    private RequestSpecification setRequestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(HOST)
                .setBasePath(API_PATH)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                .build();
    }

    private ResponseSpecification setResponseSpec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(HTTP_OK)
                .build();
    }

    @Test
    public void getUsers() {

        String id = given()
                .spec(setRequestSpec())
                .expect()
                .spec(setResponseSpec())
                .when()
                .get(endPoint)
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data[0].id");

        System.out.println(id);
    }


    @Test
    public void getUserById() {

        given()
                .spec(setRequestSpec())
                .expect()
                .spec(setResponseSpec())
                .when()
                .get(endPoint + "/24")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("data.name", equalTo("Adhiraj Patel"));
    }
}
