package com.azmat.assignment.qa;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.azmat.assignment.logger.LogFactory;
import com.azmat.assignment.logger.LoggerInterface;
import com.azmat.assignment.pojos.Body;
import com.azmat.assignment.process.MyHttpClient;
import com.azmat.assignment.process.MyHttpResponse;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
public class PostTest {

    private static final String URL = "https://www.tajawal.ae/api/hotel/ahs/search/request";

    private static final LoggerInterface LOGGER = LogFactory.instance().getLogService(PostTest.class);

    /* A valid case with dynamically generated body */
    @Test
    @Order(1)
    void testPostSuccess() {
        /* Create Post Request Body Dynamically */
        Body body = Body.getDynamicBody();

        /* HTTPClient object param - URL, RequestBody, queryParam */
        MyHttpClient myHttpClient = new MyHttpClient(URL, body.toJson().toString(), null);

        /* Invoking Post Method */
        MyHttpResponse myResponse = myHttpClient.sendPostRequest();

        LOGGER.logAsInfo("testPostSuccess", "Request Body: " + body.toJson().toString());
        LOGGER.logAsInfo("testPostSuccess", "Response: " + myResponse.getResponseBody());

        /* Assertion to verify request execution status */
        // Execution Status verification
        Assert.assertEquals(true, myResponse.isSuccessful());
        // Response body verification
        Assert.assertEquals(true, PostTest.validateResponse(myResponse.getResponseBody()));

    }

    /* A negative Test with EMPTY request body */
    @Test
    @Order(2)
    void testPostEmptyBody() {
        /* HTTPClient object param - URL, RequestBody- [EMPTY], queryParam */
        MyHttpClient myHttpClient = new MyHttpClient(URL, "", null);

        /* Invoking Post Method */
        MyHttpResponse myResponse = myHttpClient.sendPostRequest();

        LOGGER.logAsInfo("testPostEmptyBody", "Request Body" + "");
        LOGGER.logAsInfo("testPostEmptyBody", "Response: " + myResponse.getResponseBody());

        /* Assertion to verify request execution status */
        // Response Assertion
        Assert.assertEquals(false, myResponse.isSuccessful());
        // Response Verification
        Assert.assertEquals(false, PostTest.validateResponse(myResponse.getResponseBody()));

    }

    /* A negative Test with invalid request body */
    @Test
    @Order(3)
    void testPostInvalidBody() {
        /* Create Post Request Body Dynamically */
        Body body = Body.getDynamicBody();

        /* HTTPClient object param - URL, RequestBody, queryParam */
        MyHttpClient myHttpClient = new MyHttpClient(URL, body.toJson().toString().substring(0, 50), null);

        /* Invoking Post Method */
        MyHttpResponse myResponse = myHttpClient.sendPostRequest();

        LOGGER.logAsInfo("testPostInvalidBody", "Request Body: " + body.toJson().toString().substring(0, 50));
        LOGGER.logAsInfo("testPostInvalidBody", "Response: " + myResponse.getResponseBody());

        /* Assertion to verify request execution status */
        // Response status verification
        Assert.assertEquals(false, myResponse.isSuccessful());
        // Response Body Verification
        Assert.assertEquals(false, PostTest.validateResponse(myResponse.getResponseBody()));

    }

    /* Valid Test with dynamic request body - RESTASSURED */
    @Test
    @Order(4)
    void rstAsurdSuccess() {
        /* Create Post Request Body Dynamically */
        Body body = Body.getDynamicBody();
        // Set BaseURL
        RestAssured.baseURI = URL;
        RequestSpecification httpRequest = RestAssured.given();
        // Header parameters
        httpRequest.header("content-Type", "application/json");
        // Request Body
        httpRequest.body(body.toJson().toString());
        // Response Object
        Response response = httpRequest.request(Method.POST);

        LOGGER.logAsInfo("rstAsurdSuccess", "RequestBody:  " + body.toJson().toString());
        LOGGER.logAsInfo("rstAsurdSuccess", "Response: " + response.getBody().asString());

        // Assertion on Response
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("HTTP/1.1 200 OK", response.statusLine());
        Assert.assertEquals("hotel", response.jsonPath().get("type"));

    }

    /* Test without request body - RESTASSURED */
    @Test
    @Order(5)
    void rstAsurdNoBody() {
        // Set BaseURL
        RestAssured.baseURI = URL;
        RequestSpecification httpRequest = RestAssured.given();
        // Header parameters
        httpRequest.header("content-Type", "application/json");
        // Empty Body
        httpRequest.body("");
        // Response Object
        Response response = httpRequest.request(Method.POST);

        LOGGER.logAsInfo("rstAsurdNoBody", "RequestBody:  " + "No Body");
        LOGGER.logAsInfo("rstAsurdNoBody", "Response: " + response.getBody().asString());
        // Assertion on Response
        Assert.assertEquals(400, response.getStatusCode());
        Assert.assertEquals("HTTP/1.1 400 Bad Request", response.statusLine());

    }

    private static boolean validateResponse(String jsonStr) {
        boolean result = true;
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            if (!jsonObject.getString("type").equals("hotel"))
                result = false;
        } catch (JSONException e) {
            result = false;
        }
        return result;
    }

}
