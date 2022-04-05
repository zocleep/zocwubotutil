import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.validation.constraints.NotNull;

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
        message.setReplyToMessageId(update.getMessage().getMessageId());
        if(text == null) {
            text = "Not found";
        }
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendReplyMessage(@NotNull Update update, String text) {
        long chadID = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(Long.toString(chadID));
        message.setReplyToMessageId(update.getMessage().getMessageId());
        if(text == null) {
            text = "Not found";
        }
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendToFather(@NotNull Update update, String text, boolean userName) {
        SendMessage message = new SendMessage();
        message.setParseMode(ParseMode.MARKDOWN);
        message.setChatId(InfoTaker.getInfoFromFile()[0]); // id from file "your_id.txt"
        message.setReplyToMessageId(update.getMessage().getMessageId());
        if(text == null) {
        }
        if(userName) {
            if(update.getMessage().getChat().getUserName() == null) {
                text = "[" + "Username is not found" + "](tg://user?id=" + update.getMessage().getChat().getId() +")";
            } else {
                text = "[" + update.getMessage().getChat().getUserName() + "](tg://user?id=" + update.getMessage().getChat().getId() +")";
            }


        }

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
        if(update.getMessage().hasText()) {
            User user = getInfo(update);

            switch (update.getMessage().getText()) {
                case "@zcwqBot /myInfo":
                    sendMessage(update, "##################");
                    sendMessage(update, "First name: " + user.getFirstName());
                    sendMessage(update, "Last name: " + user.getLastName());
                    sendMessage(update, "Username: " + user.getUserName());
                    sendMessage(update, "ID: " + Long.toString(user.getId()));
                    sendMessage(update, "##################");
                case "/myInfo":
                    sendMessage(update, "##################");
                    sendMessage(update, "First name: " + user.getFirstName());
                    sendMessage(update, "Last name: " + user.getLastName());
                    sendMessage(update, "Username: " + user.getUserName());
                    sendMessage(update, "ID: " + Long.toString(user.getId()));
                    sendMessage(update, "##################");

                    break;

                case "/myId":
                    sendMessage(update, "##################");
                    sendMessage(update, Long.toString(user.getId()));
                    sendMessage(update, "##################");
                    break;
	  case "@zcwqBot /myId":
                    sendMessage(update, "##################");
                    sendMessage(update, Long.toString(user.getId()));
                    sendMessage(update, "##################");
                    break;

                case "/cor":
                    sendToFather(update, "#######Catch#######", false);
                    sendToFather(update, "First name: " + user.getFirstName(), false);
                    sendToFather(update, "Last name: " + user.getLastName(), false);
                    sendToFather(update, "Username: " + user.getUserName(), true);
                    sendToFather(update, "ID: " + Long.toString(user.getId()), false);
                    sendToFather(update, "###################", false);
                    break;
                case "@zcwqBot /29":
                    sendReplyMessage(update, BusParser.getMinByURL(BusParser.getBuses().get("29")));
                    break;
            }
        }
    }

}
