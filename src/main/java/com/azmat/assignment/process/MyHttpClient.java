package com.azmat.assignment.process;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class MyHttpClient {

    private String urlString;
    private String jsonInputString;
    private Map<String, String> parameters;

    public MyHttpClient() {

    }

    public MyHttpClient(String urlString, String jsonInputString, Map<String, String> parameters) {
        this.urlString = urlString;
        this.jsonInputString = jsonInputString;
        this.parameters = parameters;
    }

    public MyHttpResponse sendGetRequest() {
        MyHttpResponse response = null;
        HttpURLConnection connection = null;
        try {
            connection = this.createConnection("GET");
            this.setRequestHeader(connection);
            this.configureTimeouts(connection);

            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                response = new MyHttpResponse(true, this.inputStreamToString(connection.getInputStream()));
            } else {
                response = new MyHttpResponse(false, this.inputStreamToString(connection.getErrorStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    public MyHttpResponse sendPostRequest() {
        MyHttpResponse response = null;
        HttpURLConnection connection = null;
        try {
            connection = this.createConnection("POST");
            this.setRequestHeader(connection);
            this.configureTimeouts(connection);
            this.addJsonInputString(connection);

            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                response = new MyHttpResponse(true, this.inputStreamToString(connection.getInputStream()));
            } else {
                response = new MyHttpResponse(false, this.inputStreamToString(connection.getErrorStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    private HttpURLConnection createConnection(String requestMethod) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(
                this.urlString + (this.parameters == null ? "" : "?" + this.getParamsString(this.parameters)))
                .openConnection();
        connection.setRequestMethod(requestMethod);
        return connection;
    }

    private void configureTimeouts(HttpURLConnection connection) {
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
    }

    private void addJsonInputString(HttpURLConnection connection) throws IOException {
        if (this.jsonInputString != null) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = this.jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
            }
        }
    }

    private String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            builder.append("&");

        }
        String resultString = builder.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;

    }

    private void setRequestHeader(HttpURLConnection connection) {
        connection.setRequestProperty("Content-Type", "application/json");
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        String readBufStr;
        String response="";
        BufferedReader bufReader = new BufferedReader(new InputStreamReader((inputStream)));

        while ((readBufStr = bufReader.readLine()) != null) {

            response = readBufStr;
        }


        return response;
    }

}
