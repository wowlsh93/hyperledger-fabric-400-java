package hama.fabric;

import java.util.concurrent.ConcurrentHashMap;

public class LevelDB {
    public ConcurrentHashMap<String, String> db = new ConcurrentHashMap<>();

    public String getValue(String key){
        return db.get(key);
    }
    public void setValue(String key, String value){
        db.put(key,value);
    }
}
