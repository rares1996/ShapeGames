package Assignment.RequestService.HTTP.Interfaces;

import java.util.concurrent.Future;

import org.json.JSONObject;

public interface IWeatherClient {

    public Future<JSONObject> getLocationTemperature(String cityId) throws InterruptedException;

}
