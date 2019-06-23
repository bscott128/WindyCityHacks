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
    public static UserInterface u = new UserInterface();

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTkyMDcxNzQ1MzY4ODgzMjAx.XQ6BEg.4R-ELWcxc6fJfXHMvykbDi7Yjiw";
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.build();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String messag = event.getMessage().getContentRaw();
        if (!messag.equals("")&&(!event.getAuthor().isBot()))
            u.processCommand(event);
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay());
        if (event.getMessage().getContentRaw().equals("high")) {
            event.getChannel().sendMessage("IQ").queue();
        }
        List<Message.Attachment> attatchments = event.getMessage().getAttachments();
        if (attatchments.size() > 0) {
            List<Message.Attachment> images = new LinkedList();
            for (Message.Attachment mess : attatchments) {
                if (mess.isImage()) {
                    images.add(mess);
                }
            }
            for (Message.Attachment message : images) {
                String fileName = message.getFileName();
                int period = fileName.indexOf(".");
                String fileType = fileName.substring(period);
                int hash = message.hashCode();
                File tempFile = new File("./images/" + hash + fileType);
                message.download(tempFile);
                event.getChannel().sendMessage("Image downloaded as " + hash + fileType).queue();
            }
            event.getChannel().sendMessage("" + images.size() + " images downloaded").queue();
        }
    }



}
