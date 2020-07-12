package com.azmat.assignment.qa;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.azmat.assignment.logger.LogFactory;
import com.azmat.assignment.logger.LoggerInterface;
import com.azmat.assignment.process.MyHttpClient;
import com.azmat.assignment.process.MyHttpResponse;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
public class GetTest {

    private static final String URL = "https://www.tajawal.ae/api/hotel/ahs/v2/geo-suggest";
    private static final LoggerInterface LOGGER = LogFactory.instance().getLogService(PostTest.class);


    /* Test No 1 - 3 are executed by using HTTP*/

    /* This Test verifies Request method with valid query parameters */
    @Test
    @Order(1)
    void testValidParam() {
        /* Query parameters definition */
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("query", "paris");
        /* Create HttpClient Object */
        MyHttpClient myHttpClient = new MyHttpClient(URL, null, queryParams);
        /* Invoket Get Method */
        MyHttpResponse myResponse = myHttpClient.sendGetRequest();
        LOGGER.logAsInfo("testValidParam", "Response: " + myResponse.getResponseBody());

        /* Assertion on Response */
        Assert.assertEquals(true, myResponse.isSuccessful());
        Assert.assertEquals(true, GetTest.validateResponse(myResponse.getResponseBody()));

    }

    /* A negative test Invalid query parameters */
    @Test
    @Order(2)
    void testInvalidParam() {
        /* Query parameters definition */
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("ABCDEFGH", "123456789");
        /* Create HttpClient Object */
        MyHttpClient myHttpClient = new MyHttpClient(URL, null, queryParams);
        /* Invoket Get Method */
        MyHttpResponse myResponse = myHttpClient.sendGetRequest();
        LOGGER.logAsInfo("testInvalidParam", "Response: " + myResponse.getResponseBody());
        /* Assertion on Response */
        Assert.assertEquals(true, myResponse.isSuccessful());
        Assert.assertEquals(false, GetTest.validateResponse(myResponse.getResponseBody()));

    }

    /* A negative test with no query parameters */
    @Test
    @Order(3)
    void testNoParam() {
        /* Create HttpClient Object */
        MyHttpClient myHttpClient = new MyHttpClient(URL, null, null);
        /* Invoket Get Method */
        MyHttpResponse myResponse = myHttpClient.sendGetRequest();
        LOGGER.logAsInfo("testNoParam", "Response: " + myResponse.getResponseBody());

        /* Assertion on Response */

        Assert.assertEquals(true, myResponse.isSuccessful());
        Assert.assertEquals(false, GetTest.validateResponse(myResponse.getResponseBody()));

    }

    /* A Test with valid query params usign RestAssured */
    @Test
    @Order(4)
    void rstAsurdValidParam() {
        /* base URL to the RESTful web service */
        RestAssured.baseURI = URL;
        RequestSpecification httpRequest = RestAssured.given();
        /* Setting Query parameters */
        httpRequest.queryParam("query", "paris");
        /* Setting Header parameters */
        httpRequest.header("content-Type", "application/json");
        /* Invoke Get Method */
        Response response = httpRequest.request(Method.GET);
        LOGGER.logAsInfo("rstAsurdValidParam", "Response: " + response.getBody().asString());

        /* Assertions on response Object */
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("HTTP/1.1 200 OK", response.statusLine());
        Assert.assertEquals(true, GetTest.validateResponse(response.getBody().asString()));

    }

    /* A Test with without query params usign RestAssured */
    @Test
    @Order(5)
    void rstAsurdNoParam() {
        /* base URL to the RESTful web service */
        RestAssured.baseURI = URL;
        RequestSpecification httpRequest = RestAssured.given();
        /* Setting Query parameters */
        // httpRequest.queryParam("query","paris");
        /* Setting Header parameters */
        httpRequest.header("content-Type", "application/json");
        /* Invoke Get Method */
        Response response = httpRequest.request(Method.GET);
        LOGGER.logAsInfo("rstAsurdNoParam", "Response: " + response.getBody().asString());
        /* Assertions on response Object */
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("HTTP/1.1 200 OK", response.statusLine());
        Assert.assertEquals(false, GetTest.validateResponse(response.getBody().asString()));

    }

    /* A Test with invalid query params usign RestAssured */
    @Test
    @Order(6)
    void rstAsurdInValidParam() {
        /* base URL to the RESTful web service */
        RestAssured.baseURI = URL;
        RequestSpecification httpRequest = RestAssured.given();
        /* Setting Query parameters */
        httpRequest.queryParam("ABCDEF", "123456");
        /* Setting Header parameters */
        httpRequest.header("content-Type", "application/json");
        /* Invoke Get Method */
        Response response = httpRequest.request(Method.GET);
        LOGGER.logAsInfo("rstAsurdInValidParam", "Response: " + response.getBody().asString());

        /* Assertions on response Object */
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("HTTP/1.1 200 OK", response.statusLine());
        Assert.assertEquals(false, GetTest.validateResponse(response.getBody().asString()));

    }

    private static boolean validateResponse(String jsonStr) {
        boolean result = true;
        try {
            JSONArray hotelsArray = new JSONObject(jsonStr).getJSONArray("hotels");

            int hotelsLength = hotelsArray.length();
            if (hotelsLength == 0)
                return false;
            for (int i = 0; i < hotelsLength; i++)
                hotelsArray.getJSONObject(i).get("hotelId");
        } catch (JSONException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

}
