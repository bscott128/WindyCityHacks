import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TTTGame {
    public static int state = 0; //0-asking question, 1-writing question
    public static final byte ONE = 0b1;
    public static final byte ZERO = 0b10;
    public static final byte DEFAULT = 0b0;
    public static boolean playerTurn;//false=x player, true=o player turn
    public static String[][] table;

    public TTTGame() {
        playerTurn = false;
        table = new String[3][3];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = " ";
            }
        }
    }

    public static final String visualTable =
            "```      |   |\n" +
                    " 3 " + table[0][0] + " | " + table[0][1] + " | " + table[0][2] + "\n" +
                    "   ___|___|___\n" +
                    "      |   |\n" +
                    " 2  " + table[1][0] + " | " + table[1][1] + " | " + table[1][2] + "\n" +
                    "   ___|___|___\n" +
                    "      |   |\n" +
                    " 1  " + table[2][0] + " | " + table[2][1] + " | " + table[2][2] + "\n" +
                    "      |   |\n" +
                    "    A   B   C```";

    public static final String refreshVisualTable() {
        String vt =
                "```      |   |\n" +
                        " 3 " + table[0][0] + " | " + table[0][1] + " | " + table[0][2] + "\n" +
                        "   ___|___|___\n" +
                        "      |   |\n" +
                        " 2  " + table[1][0] + " | " + table[1][1] + " | " + table[1][2] + "\n" +
                        "   ___|___|___\n" +
                        "      |   |\n" +
                        " 1  " + table[2][0] + " | " + table[2][1] + " | " + table[2][2] + "\n" +
                        "      |   |\n" +
                        "    A   B   C```";
        return vt;
    }

    public void isDone(MessageReceivedEvent event) {
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] != " ") {
                    count++;
                }
            }
        }
        if (count == 9) {
            state = 666;
            event.getChannel().sendMessage("The game is tied.").queue();
        }
        count = 0;
        for (int i = 0; i < table.length; i++) {
            String temp = table[i][0];
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == " ") {
                    count++;
                }
            }
            if (count > 2) {
                state = 666;
                event.getChannel().sendMessage("Player " + temp + " wins").queue();
            }
            count = 0;
        }
        count = 0;
        for (int i = 0; i < table[i].length; i++) {
            String temp = table[0][i];
            for (int j = 0; j < table.length; j++) {
                if (table[j][i] == " ") {
                    count++;
                }
            }
            if (count > 2) {
                state = 666;
                event.getChannel().sendMessage("Player " + temp + " wins").queue();
            }
            count = 0;
        }

        String temp = table[0][0];
        if ((temp == "X" || temp == "O") && table[1][1] == temp && table[2][2] == temp) {
            state = 666;
            event.getChannel().sendMessage("Player " + temp + " wins").queue();
        }
        temp = table[0][2];
        if ((temp == "X" || temp == "O") && table[1][1] == temp && table[2][0] == temp) {
            state = 666;
            event.getChannel().sendMessage("Player " + temp + " wins").queue();
        }
    }


    public void next(MessageReceivedEvent event) {

        isDone(event);
        if (state != 666) {

            String message = event.getMessage().getContentRaw().toLowerCase();
            String mark;
            if (playerTurn == false) { // X player
                mark = "X";
            } else { // O player
                mark = "O";
            }

            event.getChannel().sendMessage("Player " + mark + " turn").queue();
            event.getChannel().sendMessage(refreshVisualTable()).queue();


            if (message.equals("a3")) {
                if (table[0][0] == " ") {
                    table[0][0] = mark;
                    playerTurn = !playerTurn;
                } else {
                    event.getChannel().sendMessage("That spot is already taken").queue();
                }
            } else if (message.equals("b3")) {
                if (table[0][1] == " ") {
                    table[0][1] = mark;
                    playerTurn = !playerTurn;
                } else {
                    event.getChannel().sendMessage("That spot is already taken").queue();
                }
            } else if (message.equals("c3")) {
                if (table[0][2] == " ") {
                    table[0][2] = mark;
                    playerTurn = !playerTurn;
                } else {
                    event.getChannel().sendMessage("That spot is already taken").queue();
                }
            } else if (message.equals("a2")) {
                if (table[1][0] == " ") {
                    table[1][0] = mark;
                    playerTurn = !playerTurn;
                } else {
                    event.getChannel().sendMessage("That spot is already taken").queue();
                }
            } else if (message.equals("b2")) {
                if (table[1][1] == " ") {
                    table[1][1] = mark;
                    playerTurn = !playerTurn;
                } else {
                    event.getChannel().sendMessage("That spot is already taken").queue();
                }
            } else if (message.equals("c2")) {
                if (table[1][2] == " ") {
                    table[1][2] = mark;
                    playerTurn = !playerTurn;
                } else {
                    event.getChannel().sendMessage("That spot is already taken").queue();
                }
            } else if (message.equals("a1")) {
                if (table[2][0] == " ") {
                    table[2][0] = mark;
                    playerTurn = !playerTurn;
                } else {
                    event.getChannel().sendMessage("That spot is already taken").queue();
                }
            } else if (message.equals("b1")) {
                if (table[2][1] == " ") {
                    table[2][1] = mark;
                    playerTurn = !playerTurn;
                } else {
                    event.getChannel().sendMessage("That spot is already taken").queue();
                }
            } else if (message.equals("c1")) {
                if (table[2][2] == " ") {
                    table[2][2] = mark;
                    playerTurn = !playerTurn;
                } else {
                    event.getChannel().sendMessage("That spot is already taken").queue();
                }
            } else {
                event.getChannel().sendMessage("That's not a valid coordinate").queue();
            }
        }

    }
}
