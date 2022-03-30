package Assignment.RequestService.Interfaces;

import org.json.JSONObject;

public interface IRequestService{
    JSONObject getTemperatureOnLocation(String id)  throws Exception;
}
