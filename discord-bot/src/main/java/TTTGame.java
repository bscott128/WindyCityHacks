import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
public class TTTGame {
    public static int state = 0; //0-asking question, 1-writing question
    public static byte[][] table;
    public static final byte ONE = 0b1;
    public static final byte ZERO = 0b10;
    public static final byte DEFAULT = 0b0;
    public TTTGame() {
        table = new byte[3][3];
    }
    public static final String visualTable =
            "```  \n" +
            "  \n" +
            "  ```";

    public void next(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();

        if (!isContinue) {
            state = 666; // all ogre
        }
    }
}
