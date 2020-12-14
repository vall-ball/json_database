package client;

import java.util.HashMap;
import java.util.Map;

public class ArgsParser {
    public String parse(String[] args) {
        if (args.length == 2) {
            return args[1];
        }
        if (args.length == 4) {
            //String answer = args[1] + " " + args[3];
            return args[1] + " " + args[3];
        } else {
            //String answer = args[1] + " " + args[3];
           /* StringBuilder builder = new StringBuilder();
            for (int i = 5; i < args.length; i++) {
                builder.append(args[i] + " ");
            }*/
            return args[1] + " " + args[3] + " " + args[5];
        }
    }

    public Map<String, String> parseToMap(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("type", args[1]);
        if (args.length > 2) {
            map.put("key", args[3]);
        }
        if (args.length > 4) {
            map.put("value", args[5]);
        }
        return map;
    }
}
