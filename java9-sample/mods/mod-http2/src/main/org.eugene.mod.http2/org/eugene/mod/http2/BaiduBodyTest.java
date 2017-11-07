package org.eugene.mod.http2;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class BaiduBodyTest {
    public static void main(String[] args) {
        try {
            URI baiduURI = new URI("http://www.baidu.com");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(baiduURI)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
            String body = response.body();
            System.out.println(body);

            client.sendAsync(request, HttpResponse.BodyHandler.asString())
                    .whenComplete(BaiduBodyTest::processResponse);
            TimeUnit.SECONDS.sleep(5);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void processResponse(HttpResponse<String> response, Throwable throwable) {
        if (throwable == null) {
            System.out.println("Response Status Code:" + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } else {
            System.out.println("An exception occurred while processing the HTTP request. Error: " + throwable.getMessage());
        }
    }
}
