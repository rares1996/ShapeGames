package Assignment.RequestService;

import java.time.LocalDate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import Assignment.DAL.Interfaces.ICacheRepository;
import Assignment.Entities.LocationEntity;
import Assignment.RequestService.HTTP.Interfaces.IWeatherClient;
import Assignment.RequestService.Interfaces.IRequestService;

@Service
@EnableScheduling
public class RequestService extends Thread implements IRequestService {

    @Autowired
    private IWeatherClient client;
    @Autowired
    private ICacheRepository cacheRepository;

    private final int APIlimit = 10000;
    private volatile int APIcallcount = 0;



    //reseting the daily timer for API calls made 
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void resetcounter() {
        APIcallcount = 0;
        if (!cacheRepository.checkIfEmpty()) {
            cacheRepository.emptyCache();
        }
    }


    @Override
    public JSONObject getTemperatureOnLocation(String id) throws Exception {
        var element = cacheRepository.getFromCache(id);

        // if its not in the cache
        if (element == null && APIlimit >= APIcallcount) {
            element = new LocationEntity(client.getLocationTemperature(id).get(), LocalDate.now());
            cacheRepository.saveInCache(id, element);
            APIcallcount++;
        
        // if it is in the cache, but has expired
        } else if (element != null && LocalDate.now().isAfter(element.getExpiryDay())) {
            cacheRepository.removeFromCache(id);
            APIcallcount++;
    
            if (APIlimit >= APIcallcount) {
                element = new LocationEntity(client.getLocationTemperature(id).get(), LocalDate.now());
                cacheRepository.saveInCache(id, element);
     
            }
        // if it is in the cache, but has not expired
        } else if (element != null && !LocalDate.now().isAfter(element.getExpiryDay())) {
            element = new LocationEntity(client.getLocationTemperature(id).get(), LocalDate.now());
            cacheRepository.saveInCache(id, element);
       
        }

        return element.getPayLoad();
    }

}



// things to consider ( what if a lot of people do requests in peak time, maybe
// the server will crash- maybe we should implement an hourly api limiter)
// what do we do with the unused api calls in a day, maybe use them in the next
// day?
// does time zone matter?