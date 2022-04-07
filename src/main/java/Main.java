import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        final String TOKEN = env.get("BOT_TOKEN");
        final String BOT_USERNAME = env.get("BOT_USERNAME");
        final String FATHER_ID = env.get("FATHER_ID");
        Bot bot = new Bot(TOKEN, BOT_USERNAME);
        bot.connect();
        System.out.println("Father ID: " + FATHER_ID);
    }

}


