package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileHandler {
    String fileName;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, String> load() throws FileNotFoundException {
        Map<String, String> map = new HashMap<>();
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNext()) {
            String[] s = scanner.nextLine().split("/");
            if (s.length > 1) {
                map.put(s[0], s[1]);
            }
        }
        scanner.close();
        return map;
    }

    public void save(Map<String, String> map) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            writer.write(entry.getKey() + "/" + entry.getValue() + "\n");
        }
        writer.close();
    }

    public String loadString() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        return scanner.nextLine();
    }

}
