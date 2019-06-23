import java.util.*;
/**
 * 
 */
public class Node
{
    public float value;
    public float bias;
    /**
     * Constructor for objects of class Node
     */
    public Node(float value, float bias)
    {

    }

    public Node() {
        value = (float)Math.random();
        bias = (float)Math.random(); 
    }
}
