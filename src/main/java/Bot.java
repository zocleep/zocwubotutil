import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {

    final String TOKEN;
    final String BOT_USERNAME;

    Bot(String token, String botUserName) {
        this.TOKEN = token;
        this.BOT_USERNAME = botUserName;
    }

    public void connect() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
            System.out.println("Connected.");
        } catch (TelegramApiException e) {
            System.out.println("Something wrong.");
            e.printStackTrace();
        }

    }

    public void sendMessage(@NotNull Update update, String text) {
        long chadID = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(Long.toString(chadID));
        System.out.println("1");
        System.out.println(text);
        if (text == null) {
            text = "Not found";
            System.out.println("2");
            System.out.println(text);
        }
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendReplyMessageForBuses(@NotNull Update update, String text) {
        long chadID = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(Long.toString(chadID));
        message.setReplyToMessageId(update.getMessage().getMessageId());
        if (text == null) {
            text = "Some issue, or fucking Yandex doesn't eat my proxy.";
        }
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendToFather(@NotNull Update update, String text, boolean userName) {
        Map<String, String> env = System.getenv();
        SendMessage message = new SendMessage();
        message.setParseMode(ParseMode.MARKDOWN);
        if (text == null) {
            text = "Not found";
        }
        message.setChatId(env.get("FATHER_ID")); // id from file "your_id.txt"
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public User getInfo(Update update) {
        return new User(
                update.getMessage().getChat().getFirstName(),
                update.getMessage().getChat().getLastName(),
                update.getMessage().getChat().getUserName(),
                update.getMessage().getChat().getId()
        );
    }

    @Override
    public String getBotUsername() {
        return this.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return this.TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasText()) {
            User user = getInfo(update);
            String answer;

            switch (update.getMessage().getText()) {
                case "/myInfo":
                    answer = "#######Catch#######" +
                            "\nFirst name: " + user.getFirstName() +
                            "\nLast name: " + user.getLastName() +
                            "\nUsername: " + user.getUserName() +
                            "\nID: " + user.getId() +
                            "\n###################";
                    sendMessage(update, answer);
                    break;

                case "/myId":
                    answer = "##################\n" +
                            user.getId() +
                            "\n##################";
                    sendMessage(update, answer);
                    break;

                case "/cor":
                    answer = "#######Catch#######" +
                            "\nFirst name: " + user.getFirstName() +
                            "\nLast name: " + user.getLastName() +
                            "\nUser name: [" + update.getMessage().getChat().getUserName() + "](tg://user?id=" + update.getMessage().getChat().getId() + ")" +
                            "\nID: " + user.getId() +
                            "\n###################";
                    sendToFather(update, answer, false);
                    break;
                case "/29":
                    sendReplyMessageForBuses(update, BusParser.getMinByURL(BusParser.getBuses().get("29")));
                    break;
            }
        }
    }

}
