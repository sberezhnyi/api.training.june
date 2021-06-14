import java.net.HttpURLConnection;
import java.util.List;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Log4j2
public class GoRestTest {

    private String host = "https://gorest.co.in";
    private String apiPath = "/public-api";
    private String endPoint = "/users";
    private String userName = "Howdy!";
    private String password = "";
    private String token = "d8ff4ebb9c20f22faaa4c5ec8ea3cd36ee17f57e9c3d2d6eec592f6000daa72a";


    private String id;

    //    @BeforeClass
    //    public void setUp(){
    //        RestAssured.requestSpecification = setRequestSpec();
    //    }

    private RequestSpecification setRequestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(host)
                .setBasePath(apiPath)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }

    private RequestSpecification setRequestSpecExtraHeader() {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void getUsers() {

        given()
                .log().all()
                .spec(setRequestSpec())
                .spec(setRequestSpecExtraHeader())
                .get(endPoint)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .response()
                .prettyPrint();
    }


    @Test    // ISSUE!!! Access-Control-Allow-Methods is not present in response
    public void getAllOptions() {

        List allowMethods = given(setRequestSpec())
                .options(endPoint)
                .then()
                .statusCode(HttpURLConnection.HTTP_NO_CONTENT)
                .extract()
                .headers()
                .asList();

        System.out.println(allowMethods);

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

        id = given()
                .body(body)
                .post(endPoint)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");

        System.out.println(id);

    }

    @Test     // ISSUE Patch with null - ok
    public void patchUser() {

        String body = "{\n"
                + "\"name\" : \"Pan U\"\n"
                + "}";

        given()
                .body(body)
                .patch(endPoint + "/" + id)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");

    }

}
