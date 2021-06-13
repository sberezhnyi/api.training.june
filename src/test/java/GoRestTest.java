import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import org.testng.annotations.Test;

import static http.core.HeaderConstants.AUTHORIZATION;
import static http.core.HeaderConstants.AUTHORIZATION_TYPE_BASIC;

@Log4j2
public class GoRestTest {

    private String host = "https://gorest.co.in";
    private String apiPath = "/public-api";
    private String endPoint = "/users";
    private String userName = "Howdy!";
    private String password = "";


    @Test
    public void getUsers() {

        String encoded = Base64.getEncoder()
                .encodeToString((userName + ":" + password).getBytes(StandardCharsets.UTF_8));
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put(AUTHORIZATION, AUTHORIZATION_TYPE_BASIC + encoded);

        String result = "";
        try {
            URL url = new URL(host + apiPath + endPoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            requestProperties.forEach(conn::setRequestProperty);
            InputStream output = conn.getInputStream();
            String enc = conn.getHeaderField("Content-Encoding");
            Scanner s = new Scanner(output).useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";
        } catch (IOException e) {

            System.out.printf("Failed while working with url connection. Error msg: {%s}.", e.getMessage());
        }

        System.out.println(result);

    }
}
