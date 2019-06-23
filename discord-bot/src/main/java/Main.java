import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.*;
import java.io.*;

public class Main extends ListenerAdapter {
    private static TQGame tqGame = new TQGame();
    private static TTTGame tttGame = new TTTGame();
    public static UserInterface u;
    public static String currentCommand;
    public static int game = 0;

    public static void main(String[] args) throws LoginException {
        u = new UserInterface();
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTkyMDcxNzQ1MzY4ODgzMjAx.XQ6BEg.4R-ELWcxc6fJfXHMvykbDi7Yjiw";
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.build();

    }

    public boolean isCommand(String str) {
        if (str.substring(0, 1).equals("%") && UserInterface.commands.containsKey(str.substring(1))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();
        currentCommand = message;
        if (!message.equals("") && (!event.getAuthor().isBot()) && isCommand(message))
            u.processCommand(event);
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay());
        if (message.equals("high")) {
            event.getChannel().sendMessage("IQ").queue();
        }
        if (message.equals("%tq")) {
            tqGame = new TQGame();
            game = 1;
        }
        if (game == 1 && (!event.getAuthor().isBot())) {
            tqGame.next(event);
            if (tqGame.state == 666) {
                game = 0;
            }
        }
        if (message.equals("%ttt")) {
            tttGame = new TTTGame();
            game = 2;
        }
        if (game == 2 && (!event.getAuthor().isBot())) {
            tttGame.next(event);
            if (tqGame.state == 666) {
                game = 0;
            }
        }
        List<Message.Attachment> attatchments = event.getMessage().getAttachments();
        if (attatchments.size() > 0) {
            List<Message.Attachment> images = new LinkedList();
            for (Message.Attachment mess : attatchments) {
                if (mess.isImage()) {
                    images.add(mess);
                }
            }
            for (Message.Attachment messageImage : images) {
                String fileName = messageImage.getFileName();
                int period = fileName.indexOf(".");
                String fileType = fileName.substring(period);
                int hash = messageImage.hashCode();
                File tempFile = new File("./images/" + hash + fileType);
                messageImage.download(tempFile);
                event.getChannel().sendMessage("Image downloaded as " + hash + fileType).queue();
            }
            event.getChannel().sendMessage("" + images.size() + " images downloaded").queue();
        }
    }


}
