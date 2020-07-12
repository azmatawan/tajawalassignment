package com.azmat.assignment.process;

public class MyHttpResponse {

    private boolean status;

    private String responseBody;

    public MyHttpResponse() {

    }

    public MyHttpResponse(boolean status, String responseBody) {
        this.status = status;
        this.responseBody = responseBody;
    }

    public boolean isSuccessful() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

}
