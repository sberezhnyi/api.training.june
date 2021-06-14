import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.net.HttpURLConnection;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

@Slf4j
public class GoRestTest {

    private String host = "https://gorest.co.in";
    private String apiPath = "/public-api";
    private String endPoint = "/users";
    private String userName = "Howdy!";
    private String password = "";
    private String token = "961e012678428d7ab28fb987546cb5a39a8d3445cacce0a38a93b3d5d6db40ff";

    private String id;

    @BeforeClass
    public void setUp() {
        RestAssured.config = RestAssured.config()
                .encoderConfig(EncoderConfig.encoderConfig()
                                       .appendDefaultContentCharsetToContentTypeIfUndefined(false)

                )
                .objectMapperConfig(new ObjectMapperConfig()
                                            //upper camel case for variable names in dto classes for GSON objects
                                            .gsonObjectMapperFactory(
                                                    (type, s) -> new GsonBuilder().setFieldNamingPolicy(
                                                            FieldNamingPolicy.UPPER_CAMEL_CASE)
                                                            .create()

                                            ));

        //to extract body from response as json for assertion
        RestAssured.registerParser("text/plain", Parser.JSON);
        //log on failed tests
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private RequestSpecification setRequestSpec() {

        return new RequestSpecBuilder().setBaseUri(host)
                .setBasePath(apiPath)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }

    private RequestSpecification setRequestSpecExtraHeader() {

        return new RequestSpecBuilder().setContentType(ContentType.JSON)
                .build();
    }

    private ResponseSpecification setResponseSpec() {

        return new ResponseSpecBuilder().expectStatusCode(HTTP_OK)
                .build();
    }

    @Test
    public void getUsers() {

        given().spec(setRequestSpec())
                .expect()
                .spec(setResponseSpec())
                .when()
                .get(endPoint)
                .then()
                .extract()
                .response()
                .prettyPrint();
    }

    @Test    // ISSUE!!! Access-Control-Allow-Methods is not present in response
    public void getAllOptions() {

        List allowMethods = given(setRequestSpec()).options(endPoint)
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
        String body = "{\n" + "\"email\" : \"" + generatedString + "@gmail.com\",\n" + "\"name\" : \"Pan Test\",\n"
                + "\"gender\" : \"Male\",\n" + "\"status\" : \"Active\"\n" + "}";

        id = given().body(body)
                .post(endPoint)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");

        System.out.println(id);

    }

    @Test     // ISSUE Patch with null - ok
    public void patchUser() {

        String body = "{\n" + "\"name\" : \"Pan U\"\n" + "}";

        given().body(body)
                .patch(endPoint + "/" + id)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");

    }
}