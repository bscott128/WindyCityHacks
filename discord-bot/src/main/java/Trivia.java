import java.util.*;
import java.io.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
public class Trivia{
    Set<Integer> s = new HashSet<Integer>();
    LinkedList<TriviaNode> l;
    int state = 0;
    boolean playing = false;
    public Trivia(MessageReceivedEvent event){
        l = triviaSetup();
        playing = true;
        playGame(event);
    }

    private void playGame(MessageReceivedEvent event){
        if(state == 0&&true) {

            int n = (int) (Math.random() * 50);
            while (s.contains(n)) {
                n = (int) (Math.random() * 50);
            }
            s.add(n);
            {
                TriviaNode node = l.get(n);
                String[] choices = node.choices();
                sendMessage(event, l.get(n).q + "A: " + choices[0] + "B: " + choices[1] + "C: " + choices[2] + "D: " + choices[3] + "quit");
            }
            state++;
        }
        else if(state==1&&true) {
            int n = (int) (Math.random() * 50);
            while (s.contains(n)) {
                n = (int) (Math.random() * 50);
            }

            String str = event.getMessage().getContentRaw().toLowerCase();
            if (str.substring(0, 7).equals("%trivia")) {
                str = str.substring(7, str.length());
                if (!str.equals(" a") || !str.equals(" b") || !str.equals(" c") || !str.equals(" d") || !str.equals(" quit")) {
                    str = getMessage(event);
                }
                if (str.equals("quit")) {
                    sendMessage(event, "Thanks for playing");
                    playing = false;
                } else if (str.equals(l.get(n).a)) {
                    sendMessage(event, "Correct");
                } else
                    sendMessage(event, "Wrong");
                if (s.size() == 50) {
                    sendMessage(event, "The game is finished");
                    playing = false;
                }
            }
            state = 0;
        }
    }

    private String getMessage(MessageReceivedEvent event){
        return event.getMessage().getContentRaw().toLowerCase();
    }

    public void sendMessage(MessageReceivedEvent event, String message) {
        event.getChannel().sendMessage(message).queue();
    }

    private LinkedList<TriviaNode> triviaSetup(){
        LinkedList<TriviaNode> l = new LinkedList<TriviaNode>();
        try{
            FileReader reader = new FileReader(new File("Trivia.txt"));
            Scanner scan = new Scanner(reader);
            l = takeApart(scan);
        }
        catch (Exception e){
            System.out.println(e);
        }
        System.out.println(l.size());
        return l;
    }

    private LinkedList<TriviaNode> takeApart(Scanner scan){
        LinkedList<TriviaNode> l = new LinkedList<>();
        while(scan.hasNext()){
            scan.findInLine("\"question\":\"");
            String t = scan.next();
            if(t.equals("]}"))
                break;
            while(!t.substring(t.length()-1, t.length()).equals("}")){
                t += " " + scan.next();
            }
            System.out.println("checkpoint1");
            String q = t.substring(0, t.indexOf("correct_answer")-3);
            System.out.println("checkpoint2");
            String a = t.substring(t.indexOf("correct_answer")+17, t.indexOf("incorrect_answer")-3);
            System.out.println("checkpoint3");

            String w = t.substring(t.indexOf("[")+1, t.indexOf("]"));
            String w1 = w.substring(1, w.indexOf("\"", 1));
            w = w.substring(w.indexOf(",")+1, w.length());
            String w2 = w.substring(1, w.indexOf("\"", 1));
            w = w.substring(w.indexOf(",")+1, w.length());
            String w3 = w.substring(1, w.indexOf("\"", 1));
            l.add(new TriviaNode(q, a, w1, w2, w3));
        }
        System.out.println(l.size());
        return l;
    }
}

