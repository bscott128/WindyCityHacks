import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * @author Mark Winick
 * reads file 20questions into program, plays the game, adds an extra
 * question if player won
 * @version final
 */
public class TwentyQuestions {
    private TreeNode root;
    private TreeNode current;
    private TreeNode previousCurrent;
    private int state = 0; // 0-yes/no answer, 1-new question
    private boolean isRoot = true;
    private Scanner scan;
    private PrintWriter saveWriter;
    private boolean isOver = false; // game state
    private File sheet = null;
    public static final String TITLE = "20q's (Auto-Doc�): Pharmaceuticals";
    public static final String QUESTION_NOTATION = "#Q:";
    public static final int ROOT_INDEX = 0;
    public static final int PANEL_LENGTH = 20;
    public static final int DIFFER_QUEST_ARRAY_SIZE = 2;
    private boolean isStartedUp = true;

    /**
     * main method, makes new 20questions game, plays, adds new question
     * if player won, restarts or quits based upon player input
     */
    public static void main() {

    }

    public boolean step(MessageReceivedEvent event) {
        String str = event.getMessage().getContentRaw().toLowerCase();
        if (state == 1) {
            TreeNode notAnswer = new TreeNode(previousCurrent.getValue());
            previousCurrent.setValue(QUESTION_NOTATION + str);
            previousCurrent.setRight(notAnswer);
            state = 2;
            sendMessage(event, "Now type what it is...Is it ______? (only the answer)");
        } else if (state == 2) {
            sendMessage(event, "The game is finished.");
            previousCurrent.setLeft(new TreeNode("Is it " + str + "?"));
            save();
            return false;
        } else { // state == 0
            if (str.equals("%y") || str.equals("%yes")) { // yes
                previousCurrent = current;
                current = current.getLeft();

            } else if (str.equals("%n") || str.equals("%no")) { // no
                previousCurrent = current;
                current = current.getRight();

            }
            if (current != null) {
                sendMessage(event, (String) current.getValue());
            }
            else {
                state = 1;
                sendMessage(event, "Winner, type a differentiating question.");
            }
        }
        return true;
    }

    public void sendMessage(MessageReceivedEvent event, String message) {
        event.getChannel().sendMessage(message).queue();
    }

    /**
     * constructor for game, sets up the UI, scans file into binary tree
     */
    public TwentyQuestions() {
        sheet = new File("questions.txt");
        try {
            scan = new Scanner(sheet);
            root = read(root);
            current = root;
            scan.close();
        } catch (Throwable t) {
            printThrow(t);
        }
    }

    /**
     * If player wins, makes a joptionpane input, gets the differentiating question
     * and answer as an array of strings
     */
    public String[] getDifferentiatingQuestion() {
        JTextField diffQuest = new JTextField(PANEL_LENGTH);
        JTextField diffAnswer = new JTextField(PANEL_LENGTH / 2);
        JPanel pan = new JPanel();
        pan.add(new JLabel("Give a new question (no '?' at end)"));
        pan.add(diffQuest);
        pan.add(Box.createVerticalStrut(20));
        pan.add(new JLabel("Is it ______? (only the answer)"));
        pan.add(diffAnswer);
        String[] qVals = null;
        int result = JOptionPane.showConfirmDialog(null, pan,
                "Please Enter Your Question Statements", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            qVals = new String[DIFFER_QUEST_ARRAY_SIZE];
            qVals[0] = diffQuest.getText();
            qVals[1] = diffAnswer.getText();
        }
        return qVals;
    }

    /**
     * recursive read method, reads file based upon preorder into tree
     * if question, sets right and left of the node, otherwise, must
     * be an answer and hence a leaf
     */
    public TreeNode read(TreeNode node) throws Throwable {
        if (scan.hasNext()) {
            String input = scan.nextLine();
            node = new TreeNode(input);
            if (isQuestion(input)) {
                node.setLeft(read(null));
                node.setRight(read(null));
            } else {
                return node;
            }
        }
        return node;
    }

    /**
     * detects if a string from file read input is a question by seeing
     * if it has question notation at the beginning
     */
    public boolean isQuestion(String s) {
        int index = s.indexOf(QUESTION_NOTATION);
        if (index == 0) {
            return true;
        }
        return false;
    }

    /**
     * saves game by calling recursive print method in preorder to file
     */
    public void save() {
        try {
            saveWriter = new PrintWriter(new BufferedWriter(new FileWriter(sheet)));
            saveHelper(root);
            saveWriter.close();
        } catch (Throwable t) {
            printThrow(t);
        }
    }

    /**
     * recursive save helper method, prints out tree in preorder
     */
    private void saveHelper(TreeNode node) throws Throwable {
        if (node != null) {
            saveWriter.println(node.getValue());
            saveHelper(node.getLeft());
            saveHelper(node.getRight());
        }
    }

    /**
     * prints the output of a throwable object, including stacktrace if
     * exception
     */
    public void printThrow(Throwable t) {
        System.out.println("Error Processing Files");
        System.out.println(t);
        if (t instanceof Exception)
            t.printStackTrace();
    }

    /**
     * ends system, done based upon user input or cancel button
     */
    public void end() {
        System.exit(0);
    }

    //tostring methods below
    //used just for troubleshooting

    /**
     * level order string from main program binary tree
     */
    public String levelOrder() {
        // no recursion - you must use a stack or a queue (you figure out which)
        // format should be the same as described in toString() above
        String str = "";
        Queue aq = new LinkedList();
        aq.add(root);
        while (!aq.isEmpty()) {
            TreeNode o = (TreeNode) aq.remove();
            if (o != null) {
                if (o.getLeft() != null) {
                    aq.add(o.getLeft());
                }
                if (o.getRight() != null) {
                    aq.add(o.getRight());
                }
                str += o.getValue().toString() + ", ";
            }
        }
        return anafy(str);
    }

    /**
     * adds brackets on outside of toString methods
     */
    public String anafy(String str) {
        str = "[" + str + "]";
        int index = str.indexOf(", ]");
        if (index != -1) {
            str = str.substring(0, index);
            str += "]";
        }
        index = str.indexOf(",]");
        if (index != -1) {
            str = str.substring(0, index);
            str += "]";
        }
        return str;
    }

    /**
     * returns string of binary tree in preorder
     */
    public String preOrder() {
        // should be formatted the same way as described in toString() below
        String str = preOrderRecursiveHelper(root);
        str = anafy(str);
        return str;
    }

    /**
     * tostring preorder method for binary tree root
     */
    private String preOrderRecursiveHelper(TreeNode root) {
        String str = "";
        if (root == null) {
            return "";
        } else {
            if (root.getValue() != null) {
                str += root.getValue().toString();
            } else {
                str += "null";
            }
            if (root.getLeft() != null) {
                str += ", " + preOrderRecursiveHelper(root.getLeft());
            }
            if (root.getRight() != null) {
                str += ", " + preOrderRecursiveHelper(root.getRight());
            }
        }
        return str;
    }
}
