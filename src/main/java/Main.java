import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException{
        Map<String, String> env = System.getenv();
        final String TOKEN = env.get("BOT_TOKEN");
        final String BOT_USERNAME = env.get("BOT_USERNAME");
        final String FATHER_ID = env.get("FATHER_ID");
        System.out.println("INIT=================");
        for(String someenv : env.keySet()) {
            System.out.format("%s=%s%n", someenv, env.get(someenv));
        }
        System.out.println("INIT=================");
        Bot bot = new Bot(TOKEN, BOT_USERNAME);
        bot.connect();
        System.out.println("Father ID: " + FATHER_ID);

    }

}


