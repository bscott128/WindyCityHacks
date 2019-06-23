//import net.dv8tion.jda.core.AccountType;
//import net.dv8tion.jda.core.JDABuilder;

import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

//import javax.security.auth.login.LoginException;
import java.util.*;

import java.io.*;


public class UserInterface {

    Trivia triv;
    TTTGame tictac;
    TQGame twenty;
    boolean playingTrivia, playingTicTac, playingTwenty;

    private String motd;
    private LinkedList<String> rules;

    public static Map<String, Command> commands = new HashMap(); // gotta have that O(1)

    public static Command[] validCommands =
            {
                    new Command("play", "Plays one of three games.\n\tUSAGE:\n\t\t%play, <tictactoe/20qs/trivia>\n\t"),
                    new Command("motd", "Displays the message of the day.\n\tUSAGE:\n\t\t%motd\n\t\t%motd, <MESSAGE>\n\t"),
                    new Command("rules", "Allows for the display and editing of rules.\n\tUSAGE:\n\t\t%rules\n\t\t%rules, <add>, <RULE>\n\t\t%rules, <add>, <RULENUMBER>\n\t"),
                    new Command("randomfact", "Displays a random fact.\n\tUSAGE:\n\t\t%randomfact\n\t"),
                    new Command("about", "Displays information about this bot.\n\tUSAGE:\n\t\t%about\n\t"),
                    new Command("help", "Displays information about commands.\n\tUSAGE:\n\t\t%help\n\t\t%help, <command>\n\t"),
                    new Command("contact", "Displays contact info of the developers.\n\tUSAGE:\n\t\t%contact\n\t"),
            };

    public UserInterface() {
        motd = "";
        rules = new LinkedList();
        for (Command c : validCommands)
            commands.put("%" + c.name, c);
    }

    public void processCommand(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        String[] arguments = message.split(", ");
        String command = arguments[0];
        try {
            if (message.charAt(0) != '%')
                return;
            else if (!commands.containsKey(command)) {
                message(event, "That is not a valid command. To see a list of commands, type \"%help\"\nFor information on a command, type \"%help\" followed by the name of the command you wish to know more about");
            } else if (command.equals("%play")) {
                if (arguments[1].equals("tictactoe")) {

                } else if (arguments[1].equals("20qs")) {

                } else if (arguments[1].equals("trivia")) {

                }

            } else if (command.equals("%motd")) {
                if (arguments.length == 1) {
                    message(event, motd);
                } else {
                    motd = arguments[1];
                }
            } else if (command.equals("%rules")) {
                if (arguments.length == 1) {
                    for (int i = 0; i < rules.size(); i++)
                        message(event, (1 + i) + ". " + rules.get(i));
                } else if (arguments[1].equals("add")) {
                    rules.add(arguments[2]);
                } else if (arguments[1].equals("remove")) {
                    rules.remove(Integer.parseInt(arguments[2]) - 1);
                }
            } else if (command.equals("%randomfact")) {

            } else if (command.equals("%about")) {
                message(event, "V1.0 This bot was created in under 24 hours for Windy City Hacks 2019.");
            } else if (command.equals("%help")) {
                if (arguments.length == 1) {
                    Iterator<Command> iter = commands.values().iterator();
                    String msg = "";
                    while (iter.hasNext()) {
                        Command c = iter.next();
                        msg += "**" + c.name + "**" + " - " + c.description + "\n";
                    }
                    message(event, msg);
                } else {
                    if (!commands.containsKey("%" + arguments[1]))
                        message(event, "Please enter a valid command for which you want help.");
                    else
                        message(event, commands.get("%" + arguments[1]).description);
                }
            } else if (command.equals("%contact")) {
                message(event, "Contact the developers @Scott#7134, will.schlach#8897, boozy#7833");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            message(event, "You did not provide sufficient arguments for that command, or you did not properly format your arguments. Try using \"help\" to see how it is properly used");

        }

    }

    private void message(MessageReceivedEvent event, String message) {
        event.getChannel().sendMessage(message).queue();
    }
}

class Command {
    String name, description;

    public Command(String n, String d) {
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

