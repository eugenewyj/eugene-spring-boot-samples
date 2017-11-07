package org.eugene.mod.http2;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BaiduHeaderTest {
    public static void main(String[] args) {
        try {
            URI googleUri = new URI("http://www.baidu.com");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(googleUri)
                    .method("HEAD", HttpRequest.noBody())
                    .build();
            HttpResponse<?> response = client.send(request, HttpResponse.BodyHandler.discard(null));
            System.out.println("Response Status Code:" + response.statusCode());
            System.out.println("Response Headers are:");
            response.headers()
                    .map()
                    .entrySet()
                    .forEach(System.out::println);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
