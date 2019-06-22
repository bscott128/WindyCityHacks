import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

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
                event.getAuthor().getName()+ ": " +
                event.getMessage().getContentDisplay());
        if (event.getMessage().getContentRaw().equals("test")) {
            event.getChannel().sendMessage("test").queue();
        }
    }

}
