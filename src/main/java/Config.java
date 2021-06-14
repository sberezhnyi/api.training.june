import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.parsing.Parser;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

public final class Config implements IHookable {

    private Config() {
    }

    //to apply only config part, without setting url and path
    public static void setup() {
        setup(null, null);
    }

    //for security tests to just apply timeouts, parser etc.
    public static void setup(final String baseUri) {
        setup(baseUri, null);
    }

    public static void setup(final String baseUri, final String basePath) {
        if (basePath != null) {
            RestAssured.basePath = basePath;
        }
        if (baseUri != null) {
            RestAssured.baseURI = baseUri;
        }

        RestAssured.config = RestAssured.config()
                .encoderConfig(
                        EncoderConfig.encoderConfig()
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false)

                )
                .objectMapperConfig(
                        new ObjectMapperConfig()
                                //upper camel case for variable names in dto classes for GSON objects
                                .gsonObjectMapperFactory((type, s) ->
                                        new GsonBuilder()
                                                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                                                .create()

                                )
                );

        //to extract body from response as json for assertion
        RestAssured.registerParser("text/plain", Parser.JSON);
        //log on failed tests
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        //set timeout connection
    }


    //Allure Report
    @Override
    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
        iHookCallBack.runTestMethod(iTestResult);
    }
}
