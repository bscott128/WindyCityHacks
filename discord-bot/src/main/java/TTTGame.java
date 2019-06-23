import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
public class TTTGame {
    public static int state = 0; //0-asking question, 1-writing question
    public static final byte ONE = 0b1;
    public static final byte ZERO = 0b10;
    public static final byte DEFAULT = 0b0;
    public static String[][] table;
    public TTTGame() {
        table = new String[3][3];
        for (int i = 0; i<table.length;i++) {
            for (int j = 0; j<table[i].length;j++) {
                table[i][j] = " ";
            }
        }
    }
    public static final String visualTable =
            "```      |   |\n" +
                    " 3 "+table[0][0]+" | "+table[0][1]+" | "+table[0][2]+ "\n" +
                    "   ___|___|___\n" +
                    "      |   |\n" +
                    " 2  "+table[1][0]+" | "+table[1][1]+" | "+table[1][2]+"\n" +
                    "   ___|___|___\n" +
                    "      |   |\n" +
                    " 1  "+table[2][0]+" | "+table[2][1]+" | "+table[2][2]+"\n" +
                    "      |   |\n" +
                    "    A   B   C```";

    public void next(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();

        if (!isContinue) {
            state = 666; // all ogre
        }
    }
}
