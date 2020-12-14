package server;

import java.util.HashMap;
import java.util.Map;

public class Database {
    String[] array = new String[1000];
    Map<String, String> map = new HashMap<>();

    Database() {
        for (String s : array) {
            s = "";
        }
    }

    public void set(int number, String s) {
        array[number - 1] = s;
    }

    public String get(int number) {
        return array[number - 1];
    }

    public void delete(int number) {
        array[number - 1] = "";
    }

    //////////map methods//////////

    public void set(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    public void delete(String key) {
        map.remove(key);
    }

}
