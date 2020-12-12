package server;

public class CommandParser {

    public static String text(String[] arr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 2; i < arr.length; i++) {
            builder.append(arr[i] + " ");
        }
        return builder.toString();
    }
}
