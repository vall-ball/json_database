package client;

public class ArgsParser {
    public String parse(String[] args) {

        if (args.length == 2) {
            return args[1];
        }
        if (args.length == 4) {
            String answer = args[1] + " " + args[3];
            return answer;
        } else {
            String answer = args[1] + " " + args[3];
            StringBuilder builder = new StringBuilder();
            for (int i = 5; i < args.length; i++) {
                builder.append(args[i] + " ");
            }
            return answer + " " + builder.toString();
        }
    }
}
