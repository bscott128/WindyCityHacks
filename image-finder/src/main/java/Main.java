import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTkyMDcxNzQ1MzY4ODgzMjAx.XQ6BEg.4R-ELWcxc6fJfXHMvykbDi7Yjiw";
        builder.setToken(token);
        builder.buildAsync();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName()+ ": " +
                event.getMessage().getContentDisplay());
    }

}
