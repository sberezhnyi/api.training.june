import java.net.HttpURLConnection;
import io.restassured.http.Cookies;
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
    private String token = "961e012678428d7ab28fb987546cb5a39a8d3445cacce0a38a93b3d5d6db40ff";


    public Cookies getCookies(){
        return given()
                .log().everything()
                .baseUri(host)
                .basePath(apiPath)
                .auth()
                .basic(userName, password)
                .get(endPoint)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .response()
                .detailedCookies();
    }

    @Test
    public void getUsers() {

        given()
                .log().everything()
                .baseUri(host)
                .basePath(apiPath)
                .cookies(getCookies())
                .get(endPoint)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .response()
                .prettyPrint();
    }
}
