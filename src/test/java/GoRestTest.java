import java.net.HttpURLConnection;
import lombok.extern.log4j.Log4j2;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Log4j2
public class GoRestTest {

    private String host = "https://gorest.co.in";
    private String apiPath = "/public-api";
    private String endPoint = "/users";
    private String userName = "Howdy!";
    private String password = "";


    @Test
    public void getUsers() {

        given()
                .baseUri(host)
                .basePath(apiPath)
                .auth()
                .basic(userName, password)
                .get(endPoint)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .response()
                .prettyPrint();
    }
}
