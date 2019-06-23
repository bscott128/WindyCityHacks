public class TriviaNode {
    public String q, a, w1, w2, w3;

    public TriviaNode(String q, String a, String w1, String w2, String w3){
        this.q = q;
        this.a = a;
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
    }

    public String[] choices(){
        String[] stuff = new String[4];
        int i = (int)(Math.random()*4);
        stuff[i] = a;
        i++;
        if(i==4)
            i = 0;
        stuff[i] = w1;
        i++;
        if(i==4)
            i = 0;
        stuff[i] = w2;
        i++;
        if(i==4)
            i = 0;
        stuff[i] = w3;
        return stuff;
    }

}
