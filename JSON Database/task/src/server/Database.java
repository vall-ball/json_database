package server;

public class Database {
    String[] array = new String[1000];

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
}
