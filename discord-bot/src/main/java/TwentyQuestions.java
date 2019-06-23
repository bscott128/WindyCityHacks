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
public class TwentyQuestions
{
    private TreeNode root;
    private int state; // 0-yes/no answer, 1-new question
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
        TwentyQuestions central = new TwentyQuestions();
        TreeNode current = central.root;
        while (!central.isOver) {
            boolean playerAns = central.getDialog((String)current.getValue());
            TreeNode savedLast = current; // saves last root for addition of new child nodes
            if (playerAns) // yes is chosen
                current = current.getLeft();
            else
                current = current.getRight();
            if (playerAns && current == null) {
                central.isOver = true;
            }
            else if (current == null) {
                central.isOver = true; // game is over, prevents relooping
                boolean save = central.getDialog("Winner, would you like to save and add your response?");
                if (save) {
                    String[] newNodeVals = central.getDifferentiatingQuestion();// 0:question, 1:answer
                    if (newNodeVals!=null) {
                        TreeNode notAnswer = new TreeNode(savedLast.getValue());
                        savedLast.setValue(QUESTION_NOTATION + newNodeVals[0] + "?"); // sets parent to question, no to notAnswer
                        savedLast.setLeft(new TreeNode("Is it " + newNodeVals[1] + "?"));
                        savedLast.setRight(notAnswer);//sets original to the incorrect answer
                        central.sheet = null; // resets file to read/write
                        central.save();
                    }
                }
            }
        }
        if (central.getDialog("The game is finished. Play again?")) // recursive generates new game based upon player input
            main();
    }

    /**
     * constructor for game, sets up the UI, scans file into binary tree
     */
    public TwentyQuestions() {
        sheet = new File("questions.txt");
        try {
            scan = new Scanner(sheet);
            root = read(root);
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
        JTextField diffAnswer = new JTextField(PANEL_LENGTH/2);
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
            }
            else {
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
        }
        catch (Throwable t) {
            printThrow(t);
        }
    }

    /**
     * recursive save helper method, prints out tree in preorder
     */
    private void saveHelper(TreeNode node) throws Throwable {
        if (node!=null) {
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
     * @param q for the question
     * returns a boolean from a joptionpane asking a yes/no/cancel button
     */
    public boolean getDialog(String q) {
        String current = null;
        return false;
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
            TreeNode o = (TreeNode)aq.remove();
            if (o!=null) {
                if (o.getLeft()!=null) {
                    aq.add(o.getLeft());
                }
                if (o.getRight()!=null) {
                    aq.add(o.getRight());
                }
                str+=o.getValue().toString() + ", ";
            }
        }
        return anafy(str);
    }

    /**
     * adds brackets on outside of toString methods
     */
    public String anafy(String str) {
        str = "[" + str+ "]";
        int index = str.indexOf(", ]");
        if (index!=-1) {
            str= str.substring(0,index);
            str+="]";
        }
        index = str.indexOf(",]");
        if (index !=-1) {
            str= str.substring(0,index);
            str+="]";
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
    private String preOrderRecursiveHelper(TreeNode root){
        String str = "";
        if (root == null) {
            return "";
        }
        else {
            if (root.getValue()!=null) {
                str+=root.getValue().toString();
            }
            else {
                str+="null";
            }
            if (root.getLeft()!=null) {
                str+=", " + preOrderRecursiveHelper(root.getLeft());
            }
            if (root.getRight()!=null) {
                str+=", " + preOrderRecursiveHelper(root.getRight());
            }
        }
        return str;
    }
}
