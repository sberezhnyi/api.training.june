import http.core.HttpRequestsBasicAuth;
import lombok.extern.log4j.Log4j2;

import org.testng.annotations.Test;


@Log4j2
public class GoRestTest {

    private String host = "https://gorest.co.in";
    private String apiPath = "/public-api";
    private String endPoint = "/users";
    private String userName = "Howdy!";
    private String password = "";


    HttpRequestsBasicAuth httpRequestsBasicAuth = new HttpRequestsBasicAuth();


    @Test
    public void getUsers() {

        log.info(httpRequestsBasicAuth.doGetRequest(host + apiPath + endPoint, userName, password));
    }
}
