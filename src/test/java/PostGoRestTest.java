import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.log4j.Log4j2;
import request.User;
import response.UserPostResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static util.PropertiesLoader.API_PATH;
import static util.PropertiesLoader.HOST;
import static util.PropertiesLoader.TOKEN;

@Log4j2
public class PostGoRestTest {

    private String endPoint = "/users";
    private String id;

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
    public void postUser() {

        String generatedString = RandomStringUtils.random(10, true, false);

        User newUser = new User();
        newUser.setEmail(generatedString + "@gmail.com");
        newUser.setName("Sergii Berezhnyi");
        newUser.setGender("Male");
        newUser.setStatus("Active");

        UserPostResponse response = given(setRequestSpec())
                .contentType(ContentType.JSON)
                .body(newUser)
                .post(endPoint)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .response()
                .as(UserPostResponse.class, ObjectMapperType.JACKSON_2);


        log.info(response.getData().getId());
        log.info(response.getData().getName());

        log.info(response.getData().getCreatedAt());
        log.info(response.getData().getUpdatedAt());

    }
}
