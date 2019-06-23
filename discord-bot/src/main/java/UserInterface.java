//import net.dv8tion.jda.core.AccountType;
//import net.dv8tion.jda.core.JDABuilder;

import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
//import net.dv8tion.jda.core.hooks.ListenerAdapter;

//import javax.security.auth.login.LoginException;
import java.util.*;

import java.util.*;


public class UserInterface {

    public static Map<String, Command> commands = new HashMap(); // gotta have that O(1)

    public static Command[] validCommands =
            {
                    new Command("help", "USAGE %help <command> | Used to gain information about commands."),
                    new Command("hello", "USAGE %hello | Greets the user who used the command")
            };

    public UserInterface()
    {
        for(Command c : validCommands)
            commands.put("%"+c.name,c);
    }

    public void processCommand(MessageReceivedEvent event)
    {
        String message = event.getMessage().getContentRaw();
        String[] arguments = message.split(" ");
        String command = arguments[0];
        try {
            if (!commands.containsKey(command)) {
                message(event, "That is not a valid command. To see a list of commands, type \"%help\"\nFor information on a command, type \"%help\" followed by the name of the command you wish to know more about");
            }else if(command.equals("%hello"))
            {
                message(event, "Hello!");
            }else if (command.equals("%help")) {
                if(arguments.length == 1)
                {
                    Iterator<Command> iter  = commands.values().iterator();
                    while(iter.hasNext())
                    {
                        Command c = iter.next();
                        message(event, "**" + c.name + "**" + " - " + c.description);
                    }
                }else
                {
                    message(event, commands.get(arguments[1]).description);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            message(event, "You did not provide sufficient arguments for that command. Try using \"help\" to see how it is properly used")
        }
    }

    private void message(MessageReceivedEvent event, String message)
    {
        event.getChannel().sendMessage(message).queue();
    }

}

class Command
{
    String name, description;
    public Command(String n, String d)
        {
            name = n;
            description = d;
        }
}
/*public class UserInterface{
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

}*/

