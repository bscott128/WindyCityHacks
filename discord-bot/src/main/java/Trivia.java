import java.util.*;
import java.io.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
public class Trivia{
    LinkedList<TriviaNode> l;
    public Trivia(MessageReceivedEvent event){
        l = triviaSetup();
        playGame(event);
    }

    private void playGame(MessageReceivedEvent event){
        boolean playing = true;
        Set<Integer> s = new HashSet<Integer>();
        while(playing){
            int n = (int)(Math.random()*50);
            while(s.contains(n)) {
                n = (int)(Math.random() * 50);
            }
            s.add(n);
            {
                TriviaNode node = l.get(n);
                String[] choices = node.choices();
                sendMessage(event, l.get(n).q);
                sendMessage(event, "A: "+choices[0]);
                sendMessage(event, "B: "+choices[1]);
                sendMessage(event, "C: "+choices[2]);
                sendMessage(event, "D: "+choices[3]);
                sendMessage(event, "Quit");
            }
            String str = event.getMessage().getContentRaw().toLowerCase();
            {
                if(!str.equals("a")||!str.equals("b")||!str.equals("c")||!str.equals("d")||!str.equals("quit")){
                    str = getMessage(event);
                }
                if(str.equals("quit")){
                    sendMessage(event, "Thanks for playing");
                    playing = false;
                }
                else if(str.equals(l.get(n).a)){
                    sendMessage(event, "Correct");
                }
                else
                    sendMessage(event, "Wrong");
                if (s.size() == 50) {
                    sendMessage(event, "The game is finished");
                    playing = false;
                }
            }
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
            //System.out.println(t);
            String q = t.substring(0, t.indexOf("correct_answer")-3);
            String a = t.substring(t.indexOf("correct_answer")+17, t.indexOf("incorrect_answer")-3);
            String w = t.substring(t.indexOf("[")+1, t.indexOf("]"));
            String w1 = w.substring(1, w.indexOf("\"", 1));
            w = w.substring(w.indexOf(",")+1, w.length());
            String w2 = w.substring(1, w.indexOf("\"", 1));
            w = w.substring(w.indexOf(",")+1, w.length());
            String w3 = w.substring(1, w.indexOf("\"", 1));
            l.add(new TriviaNode(q, a, w1, w2, w3));
            //System.out.println(q + a + w1 + w2 + w3);
        }
        return l;
    }
}

