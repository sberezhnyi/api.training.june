package http.core;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static http.core.HeaderConstants.APPLICATION_JSON;
import static http.core.HeaderConstants.AUTHORIZATION;
import static http.core.HeaderConstants.AUTHORIZATION_TYPE_BASIC;
import static http.core.HeaderConstants.CONTENT_TYPE;

public class HttpRequestsBasicAuth {

    private HttpRequests httpRequests = new HttpRequests();

    public String doPostRequest(final String resourceUrl, final String login, final String password, final String postData) {
        String encoded = Base64.getEncoder().encodeToString((login + ":" + password).getBytes(StandardCharsets.UTF_8));
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put(AUTHORIZATION, AUTHORIZATION_TYPE_BASIC + encoded);
        requestProperties.put(CONTENT_TYPE, APPLICATION_JSON);
        return httpRequests.doRequestWithPayload(resourceUrl, RequestType.POST,
                requestProperties, postData);
    }

    public String doGetRequest(final String resourceUrl, final String login, final String password) {
        String encoded = Base64.getEncoder().encodeToString((login + ":" + password).getBytes(StandardCharsets.UTF_8));
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put(AUTHORIZATION, AUTHORIZATION_TYPE_BASIC + encoded);
        return httpRequests.doGetRequest(resourceUrl,
                requestProperties);
    }

    public String doPutRequest(final String resourceUrl, final String login, final String password, final String putData) {
        String encoded = Base64.getEncoder().encodeToString((login + ":" + password).getBytes(StandardCharsets.UTF_8));
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put(AUTHORIZATION, AUTHORIZATION_TYPE_BASIC + encoded);
        requestProperties.put(CONTENT_TYPE, APPLICATION_JSON);
        return httpRequests.doRequestWithPayload(resourceUrl, RequestType.PUT,
                requestProperties, putData);
    }

    public String doDeleteRequest(final String resourceUrl, final String login, final String password, final String deleteData) {
        String encoded = Base64.getEncoder().encodeToString((login + ":" + password).getBytes(StandardCharsets.UTF_8));
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put(AUTHORIZATION, AUTHORIZATION_TYPE_BASIC + encoded);
        requestProperties.put(CONTENT_TYPE, APPLICATION_JSON);
        requestProperties.put("Accept-Encoding", "gzip, deflate");
        requestProperties.put("X-Requested-By", "XMLHttpRequest");
        return httpRequests.doRequestWithPayload(resourceUrl, RequestType.DELETE,
                requestProperties, deleteData);
    }
}
