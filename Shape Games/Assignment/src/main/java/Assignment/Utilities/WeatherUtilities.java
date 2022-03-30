package Assignment.Utilities;

import org.json.JSONObject;

public class WeatherUtilities{
   public static boolean IsLocationAboveTemperature(String units, int desiredTemperature, JSONObject locationObj){

        var locationTemp = locationObj.getJSONArray("list").getJSONObject(0).getJSONObject("main").getFloat("temp");
      
        if(units == "celsius"){
            desiredTemperature = (desiredTemperature *  (9 / 5)) + 32;
        }

        return locationTemp >= desiredTemperature;
   }
}