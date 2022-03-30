package Assignment.DAL;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import Assignment.Entities.LocationEntity;

@Repository
public class CacheRepository implements Assignment.DAL.Interfaces.ICacheRepository {

    HashMap<String, LocationEntity> cacheList = new HashMap<String, LocationEntity>();

    @Override
    public LocationEntity getFromCache(String key) {
        return cacheList.get(key);
    }

    @Override
    public void saveInCache(String key, LocationEntity location) {
        if(cacheList.get(key) != null){
            cacheList.remove(key);
        }

        cacheList.put(key, location);
    }
    
    @Override
    public void removeFromCache(String key) {
        if(cacheList.get(key) != null){
            cacheList.remove(key);
        }
    }

    @Override
    public void emptyCache() {
        cacheList.clear();
    }

    @Override
    public boolean checkIfEmpty() {
        if(cacheList.isEmpty()) {
            return true;
        } else { return false;}
    }
      
}

