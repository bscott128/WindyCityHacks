import java.util.*;
import java.io.*;
/*
 * change = (learning rate * delta * value) + (momentum * pastChange)
 * 
 * node value = ((input1 * edge) + (input2 * edge) + (input?* edge)+ bias (at the node) (edges are between input nodes and target node)
 * 
 * run the node value through the sigmoid function, then go to the output nodes repeating the value with the edge and bias
 * 
 * then find error and delta, then back propogate adjusting all weights and biases
 * 
 * go back and alter edges and biases, adjusting based on learning rate and momentum
 * 
 */
public class Network
{
    public final int outputNodes = 2;
    public final int inputNodes = 256;
    public final int hiddenLayerNodes = 16;
    public float averageErrorRate;
    public float errorAccumulation;
    public int iterations;
    public float learningRate = .1f;
    public float momentum = .15f;
    public List<float[][]> zeroPics;
    public List<float[][]> onePics;
    public Node[] inNodes;
    public Node[] midNodes;
    public Node[] outNodes;
    public Map<Node,Map<Node,Edge>> edges;
    /**
     * Constructor for objects of class Network
     */
    public Network()
    {
        iterations = 0;
        edges = new HashMap();
        initializeImages();
        initializeNodes();
        //edges
        //in->mid a, b
        //mid->out a, b
    }

    public static void main() {
        Network net = new Network();

    }
    //  node value = ((input1 * edge) + (input2 * edge) + (input?* edge)+ bias (at the node) (edges are between input nodes and target 
    public void iterate() {
        for (int i = 0; i<midNodes.length; i++) {
            Node mid = midNodes[i];
            float val = 0;
            for (int j = 0; j<inNodes.length; j++) {
                Node in = inNodes[j];
                Edge e = edges.get(in).get(mid);
                val+=in.value*e.weight;
            }
            val+=mid.bias;
            val = sigmoid(val);
            midNodes[i].value = val;
        }
        for (int i = 0; i<outNodes.length; i++) {
            Node out = outNodes[i];
            float val = 0;
            for (int j = 0; j<midNodes.length; j++) {
                Node mid = midNodes[j];
                Edge e = edges.get(mid).get(out);
                val+=mid.value*e.weight;
            }
            val+=out.bias;
            outNodes[i].value = val;
        }
    }

    public void initializeNodes() {
        inNodes = new Node[inputNodes];
        midNodes = new Node[hiddenLayerNodes];
        outNodes = new Node[outputNodes];
        for (int i = 0; i<outNodes.length; i++) {
            outNodes[i] = new Node();
        }
        for (int i = 0; i<midNodes.length; i++) {
            midNodes[i] = new Node();
            for (int j = 0; j<outNodes.length; j++) {
                Map<Node,Edge> edge = new HashMap();
                edge.put(outNodes[j],new Edge());
                edges.put(midNodes[i],edge);
            }
        }
        for (int i = 0; i<inNodes.length; i++) {
            inNodes[i] = new Node(-1,-1);
            for (int j = 0; j<midNodes.length; j++) {
                Map<Node,Edge> edge = new HashMap();
                edge.put(midNodes[j],new Edge());
                edges.put(inNodes[i],edge);
            }
        }
    }

    public static float sigmoid(float x)
    {
        return (float)(1 / (1 + Math.exp(-x)));
    }

    public void initializeImages() {
        String[] zeros = readFileOpenOptions("nums/zeros/");
        String[] ones = readFileOpenOptions("nums/ones/");
        LinkedList<float[][]> zeroMaps = new LinkedList();
        LinkedList<float[][]> oneMaps = new LinkedList();
        for (int i = 0; i<zeros.length; i++) {
            Image img = new Image(zeros[i]);
            zeroMaps.add(img.getGrayMap());
        }
        for (int i = 0; i<ones.length; i++) {
            Image img = new Image(ones[i]);
            oneMaps.add(img.getGrayMap());
        }
        zeroPics = zeroMaps;
        onePics = oneMaps;
    }

    /**
     * gets a list of strings corresponding to all ppm files in folder
     */
    public String[] readFileOpenOptions(String master) {
        File folder = new File(master);
        File[] listFiles = folder.listFiles();
        ArrayList<String> ppms = new ArrayList<String>();
        for (File x: listFiles) {
            String name = x.getName();
            int size = name.length();
            String fileForm = name.substring(size-3);
            if (fileForm.equals("ppm"))
                ppms.add(name);
        }
        return ppms.toArray(new String[0]);
    }
}
