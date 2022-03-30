package Assignment.RequestService.HTTP;

import org.json.JSONObject;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import Assignment.RequestService.HTTP.Interfaces.IWeatherClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class WeatherClient implements IWeatherClient {


    private final String requestURL = "https://api.openweathermap.org/data/2.5/forecast?id=%s&appid=%s";
    private final String apiKey = "a8193543ecf27622013b0f9265986190";

    HttpClient client = HttpClient.newHttpClient();

    @Override
    @Async
    public Future<JSONObject> getLocationTemperature(String cityId) throws InterruptedException{
 
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(String.format(requestURL, cityId, apiKey))).build();
        var future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenApply((r -> new JSONObject(r)));
        return future;
    };
}
