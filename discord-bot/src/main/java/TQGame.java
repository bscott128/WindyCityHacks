import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
//import net.dv8tion.jda.core.hooks.ListenerAdapter;

//import javax.security.auth.login.LoginException;
import java.util.*;
public class TQGame {

    public int state = 0; //0-asking question, 1-writing question
    TwentyQuestions tq;
    public TQGame() {
        tq = new TwentyQuestions();
    }
    public void next(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();
        if (event.getMessage().getContentRaw().toLowerCase().equals("%end")) {
            state = 666;
            event.getChannel().sendMessage("Twenty Questions process ended").queue();
        }
        else {
            boolean isContinue = tq.step(event);
            if (!isContinue) {
                state = 666; // all ogre
            }
        }
    }

}
