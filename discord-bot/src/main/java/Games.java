import java.util.*;

public class Games {

    public Games(String game){
        if(game.equals("trivia")){
            trivia();
        }
    }

    private void trivia(){//bot asks a question - user types in a, b, c, d, or q
        //LinkedList<TriviaNode> l = triviaHelper();
    }

    private LinkedList<TriviaNode> triviaHelper(){
        LinkedList<TriviaNode> l = new LinkedList<TriviaNode>();
        Scanner scan = new Scanner("Trivia.txt.rtf");
        while(scan.hasNext()){

        }
        return l;
    }

}

