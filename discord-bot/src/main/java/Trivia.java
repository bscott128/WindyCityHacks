import java.util.*;
import java.io.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Trivia {
    Set<Integer> s = new HashSet<Integer>();
    LinkedList<TriviaNode> l;
    int state = 0;
    boolean playing = false;
    public int n;
    public String[] choices;

    public Trivia(MessageReceivedEvent event) {
        l = triviaSetup();
        playing = true;
        playGame(event);
    }

    public int getIndexRightAnswer() {
        String right = l.get(n).a;
        for (int i = 0; i<choices.length;i++) {
            if (choices[i].equals(right)) {
                return i;
            }
        }
        return -1;
    }

    public void playGame(MessageReceivedEvent event) {
        if (state == 0 && true) {

            n = (int) (Math.random() * 50);
            while (s.contains(n)) {
                n = (int) (Math.random() * 50);
            }
            s.add(n);
            {
                TriviaNode node = l.get(n);
                choices = node.choices();
                String mes = l.get(n).q + "\n A: " + choices[0] + "\n B: " + choices[1] + "\n C: " + choices[2] + "\n" +
                        "D: " + choices[3] + "\n quit";
                sendMessage(event, mes);
            }
            state++;
        } else if (state == 1 && true) {

            String str = event.getMessage().getContentRaw().toLowerCase();
            if (str.substring(0, 1).equals("%")) {
                str = str.substring(1);
                if (!str.equals("a") || !str.equals("b") || !str.equals("c") || !str.equals("d") || !str.equals("quit")) {
                    str = getMessage(event);
                }
                state++;
            }
        } else if (state == 2 && playing) {
            String str = event.getMessage().getContentRaw().toLowerCase().substring(1);
            int letterChoice = strToInt(str);
            if (str.equals("end")) {
                sendMessage(event, "Thanks for playing");
                playing = false;
            } else if (letterChoice==getIndexRightAnswer()) {
                sendMessage(event, "Correct");
            } else {
                sendMessage(event, "Wrong");
            }
            if (s.size() == 50) {
                sendMessage(event, "The game is finished");
                playing = false;
            }
            // }
            state = 0;
        }
    }

    public int strToInt(String s) {
        char c = s.charAt(0);
        if (c=='a') {
            return 0;
        }
        if (c=='b') {
            return 1;
        }
        if (c=='b') {
            return 2;
        }
        if (c=='b') {
            return 3;
        }
        return -1;
    }



    private String getMessage(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().toLowerCase();
    }

    public void sendMessage(MessageReceivedEvent event, String message) {
        event.getChannel().sendMessage(message).queue();
    }

    private LinkedList<TriviaNode> triviaSetup() {
        LinkedList<TriviaNode> l = new LinkedList<TriviaNode>();
        try {
            FileReader reader = new FileReader(new File("Trivia.txt"));
            Scanner scan = new Scanner(reader);
            l = takeApart(scan);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(l.size());
        return l;
    }

    private LinkedList<TriviaNode> takeApart(Scanner scan) {
        LinkedList<TriviaNode> l = new LinkedList<>();
        while (scan.hasNext()) {
            scan.findInLine("\"question\":\"");
            String t = scan.next();
            if (t.equals("]}"))
                break;
            while (!t.substring(t.length() - 1, t.length()).equals("}")) {
                t += " " + scan.next();
            }
            System.out.println("checkpoint1");
            String q = t.substring(0, t.indexOf("correct_answer") - 3);
            System.out.println("checkpoint2");
            String a = t.substring(t.indexOf("correct_answer") + 17, t.indexOf("incorrect_answer") - 3);
            System.out.println("checkpoint3");

            String w = t.substring(t.indexOf("[") + 1, t.indexOf("]"));
            String w1 = w.substring(1, w.indexOf("\"", 1));
            w = w.substring(w.indexOf(",") + 1, w.length());
            String w2 = w.substring(1, w.indexOf("\"", 1));
            w = w.substring(w.indexOf(",") + 1, w.length());
            String w3 = w.substring(1, w.indexOf("\"", 1));
            l.add(new TriviaNode(q, a, w1, w2, w3));
        }
        System.out.println(l.size());
        return l;
    }
}

