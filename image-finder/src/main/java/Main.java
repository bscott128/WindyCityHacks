import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.*;
import java.io.*;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTkyMDcxNzQ1MzY4ODgzMjAx.XQ6BEg.4R-ELWcxc6fJfXHMvykbDi7Yjiw";
        builder.setToken(token);
        builder.addEventListener(new Main());
        builder.buildAsync();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
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
                String randomName = "" + (Math.random());
                randomName = randomName.substring(2,5);
                String fileName = message.getFileName();
                int period = fileName.indexOf(".");
                String fileType = fileName.substring(period);
                File tempFile = new File("./images/"+message.hashCode()+fileType);
                message.download(tempFile);
            }
            event.getChannel().sendMessage("" + images.size()).queue();
        }
    }

}
