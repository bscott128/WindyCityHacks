// import net.dv8tion.jda.core.AccountType;
// import net.dv8tion.jda.core.JDABuilder;
// import net.dv8tion.jda.core.entities.Message;
// import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
// import net.dv8tion.jda.core.hooks.ListenerAdapter;

// import javax.security.auth.login.LoginException;
// import java.util.*;

public class UserInterface{
    Set<String> commands = new HashSet<String>();
    MessageReceivedEvent event;
    String command;
    boolean commanding = false;
    User user;

    public static void main(MessageReceivedEvent event){
        this.event = event;
        command = event.getMessage().getContentRaw();
        user = event.getAuthor();
        processCommand(command);
    }

    private void processCommand(String command){
        if(command!=null&&command.substring(0, 1).equals("%")){//moves to using the commands
            command = command.substring(1, command.length());
            commanding = true;
            if(!commands.contains(command)||command.equals("help")){//tried a command that doesn't exist
                help();
            }
            else if(command.equals("fun")){
                fun();
            }
            commanding = false;
        }
    }

    private void declareCommands(){
        commands.add("help");
        commands.add("fun");
    }

    private void help(){
        if(commanding){
            event.getChannel().sendMessage("Commands:").queue();
            event.getChannel().sendMessage("%help -- retrieves commands");
            event.getChannel().sendMessage("%fun -- testing command");
            
        }
    }

    private void fun(){//testing

    }

}

