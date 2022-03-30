package Assignment.Web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Assignment.RequestService.Interfaces.IRequestService;
import Assignment.Utilities.WeatherUtilities;
import Assignment.Web.Responses.LocationReturnEntity;

@RestController
@Component
public class Controller {

    @Autowired
    private IRequestService reqService;



    @GetMapping(value = "/weather/locations/{locationId}", produces = "application/json")

    public ResponseEntity<Object> getTemperatureNextFiveDays(@PathVariable("locationId") String locationId)
            throws Exception {

        return new ResponseEntity<Object>(reqService.getTemperatureOnLocation(locationId).toString(), HttpStatus.OK);
    }




    @GetMapping(value = "/weather/summary", produces = "application/json")

    public ResponseEntity<Object> getTemperatureOnLocations(@RequestParam(required = true) String unit,
            @RequestParam(required = true) int temperature, @RequestParam(required = true) String locations)
            throws Exception {

 
        var locationIdsList = locations.split(",", 0);

        var locationsToReturn = new ArrayList<LocationReturnEntity>();

        for (String locationId : locationIdsList) {
            LocationReturnEntity location = new LocationReturnEntity();

            try {
                var locationObj = reqService.getTemperatureOnLocation(locationId);

                if (WeatherUtilities.IsLocationAboveTemperature(unit, temperature, locationObj)) {
                    location.payload = locationObj.toString();
                } 
            } catch (Exception e) {

            }

            locationsToReturn.add(location);
        }

        return new ResponseEntity<Object>(locationsToReturn, HttpStatus.OK);
    }
}
