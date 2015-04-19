package io.sytac.training.repos;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DataRepository {

    public final Map<String, String> data = new ConcurrentHashMap<String, String>();

    public void addData(String name, String value) {
        data.put(name, value);
    }

    public String  get(String name) {
        return data.get(name);
    }

    public Map<String, String> getAll() {
        return new HashMap<String, String>(data);
    }

}
