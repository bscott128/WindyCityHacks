//import net.dv8tion.jda.core.AccountType;
//import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
//import net.dv8tion.jda.core.hooks.ListenerAdapter;

//import javax.security.auth.login.LoginException;
import java.util.*;

public class UserInterface{
    Set<String> commands = new HashSet<String>();
    MessageReceivedEvent event;
    String command;
    boolean commanding = false;
    User user;

    public void main(MessageReceivedEvent event){
        declareCommands();
        this.event = event;
        command = event.getMessage().getContentRaw();
        user = event.getAuthor();
        processCommand(command);
    }

    private void processCommand(String command){
        if((command!=null||!command.equals(""))&&command.substring(0, 1).equals("%")){//moves to using the commands
            command = command.substring(1);
            commanding = true;
            if(!commands.contains(command)||command.equals("help")){//tried a command that doesn't exist
                help();
            }
            else{
                image();
            }
            commanding = false;
        }
    }

    private void declareCommands(){
        commands.add("help");
        //File folder = new File(".");
        //File[] listFiles = folder.listFiles();

    }

    private void help(){
        if(commanding){
            event.getChannel().sendMessage("% + tag -- displays all images with tagged object").queue();
        }
    }

    private void image() {
    }

}

