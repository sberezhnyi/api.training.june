import java.net.HttpURLConnection;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import lombok.extern.log4j.Log4j2;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Log4j2
public class GoRestTest {

    private String host = "https://gorest.co.in";
    private String apiPath = "/public-api";
    private String endPoint = "/users";
    private String userName = "Howdy!";
    private String password = "";
    private String token = "961e012678428d7ab28fb987546cb5a39a8d3445cacce0a38a93b3d5d6db40ff";


    @Test
    public void getUsers() {

        given()
                .log()
                .everything()
                .baseUri(host)
                .basePath(apiPath)
                .header("Authorization", "Bearer " + token)
                .request("TRACE", endPoint)
                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_METHOD)
                .extract()
                .response()
                .prettyPrint();
    }



    @Test
    public void postUser() {

        String generatedString = RandomStringUtils.random(10, true, false);
        String body = "{\n"
                + "\"email\" : \"" + generatedString + "@gmail.com\",\n"
                + "\"name\" : \"Pan Test\",\n"
                + "\"gender\" : \"Male\",\n"
                + "\"status\" : \"Active\"\n"
                + "}";

        given()
                .log()
                .everything()
                .baseUri(host)
                .basePath(apiPath)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(body)
                .post(endPoint)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .response()
                .prettyPrint();

    }
}
