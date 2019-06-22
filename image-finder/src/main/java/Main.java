import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import javax.security.auth.login.LoginException;
import java.util.*;

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
        if (event.getMessage().getContentRaw().equals("high")) {
            event.getChannel().sendMessage("IQ").queue();
        }
        List<Message.Attachment> attatchments = event.getMessage().getAttachments();
        if (attatchments.size()>0) {
            List images = new LinkedList();
            for (Message.Attachment mess : attatchments) {
                if (mess.isImage()) {
                    images.add(mess);
                }
            }
            event.getChannel().sendMessage(""+images.size()).queue();
        }
    }

}
