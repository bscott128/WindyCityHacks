import java.io.*;
import java.util.*;
/**
 * Reads from a ppm file specified by GUI and output to file also specified
 * GUI also asks for image effects in a loop until it is exited
 * or "print" is selected.
 * 
 * stores values as ints, but can print such ints to binary byte strings and converts from such.
 * (can convert between, although irfranviewer can't seem to decipher binary byte strings replacing the ints, and
 * P6 as its new header for some reason)
 * @author Mark W
 * @version .1
 */
public class Image
{
    private int[][] imgMap;
    private Pixel[][] pMap;
    private int row;
    private int col;
    private int mColorVal;
    private String format;
    private int NUM_OF_COLORS = 255;
    private int BLACK_COLOR = 0;
    private int CHECK_SUM_MAX = 10;
    private String input;
    private float[][] grayMap;
    /**
     * Constructor
     */
    public Image(String file)
    {
        input = file;
        readPPMFile();
        doGrayMap();
    }

    public float[][] getGrayMap() {
        return grayMap;   
    }

    public void doGrayMap() {
        float[][] mappa = new float[16][16];
        for (int i = 0; i<mappa.length; i++) {
            for (int j = 0; j<mappa[0].length; j++) {
                mappa[i][j] = pMap[i][j].gray();
            }
        }
        grayMap = mappa;
    }

    public void printGrayMap() {
        for (int i = 0; i<grayMap.length; i++) {
            for (int j = 0; j<grayMap[0].length; j++) {
                System.out.print(grayMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * get method to retrieve the array of all of an image's color values
     */
    public int[][] getColorArray() {
        return imgMap;
    }

    /**
     * returns image color depth
     */
    public int getColorDepth() {
        return mColorVal;
    }

    /**
     * Asks for input, output, and image effects to be applied
     */
    public static void main(String[] args)
    {
        try {
            System.out.println("\f");
            Image i = new Image("C:\\Users\\Administrator.WINDOWS-Q3MV2IJ\\Desktop\\DiscordTagger\\src\\nums\\ones\\0.ppm");
            i.printGrayMap();
            //ie.printImgMap(oName);
        }
        catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * converts array of ints to an array of pixels, for easier management
     */
    public void convertToPixelArray() {
        pMap = new Pixel[row][];
        for (int i = 0; i<row; i++) {
            Pixel[] pRow = new Pixel[col];
            for (int j = 0; j<col*NUM_OF_COLORS; j+=NUM_OF_COLORS) {
                pRow[j/NUM_OF_COLORS] = new Pixel(imgMap[i][j], imgMap[i][j+1], imgMap[i][j+2]);
            }
            pMap[i] = pRow;
        }
    }

    /**
     * deconverts array of pixels to ints so it can be printed
     */
    public void deconvertPixelArray() {
        row = pMap.length;
        col = pMap[0].length;
        imgMap = new int[row][col*NUM_OF_COLORS];
        for (int i = 0; i<row; i++) {
            for (int j = 0; j<col*NUM_OF_COLORS; j+=NUM_OF_COLORS) {
                imgMap[i][j] = pMap[i][j/NUM_OF_COLORS].gR();
                imgMap[i][j+1] = pMap[i][j/NUM_OF_COLORS].gG();
                imgMap[i][j+2] = pMap[i][j/NUM_OF_COLORS].gB();
            }
        }
    }

    /**
     * gets all the header parameters of a file with input of that file's scanner
     * throws exception if invalid parameters
     */
    public void getPPMFileHeaderParameters(Scanner scan) throws ImageEditorException {
        format = scan.next();
        int checkSum = 0;
        while (format==null) {
            format = scan.next();
            if (format.indexOf("#")!=-1) {
                format = null;
            }
            checkSum++;
            if (checkSum > CHECK_SUM_MAX) {
                throw new ImageEditorException("Invalid ppm file formatting, too many invalid attempts");   
            }
        }
        try {
            int v = format.indexOf("P");
            format = format.substring(v, v+2); // throws exception if i =-1
        }
        catch (Exception e) {
            throw new ImageEditorException("invalid ppm file format");
        }
        try {
            col = scan.nextInt(); // # first = col
            row = scan.nextInt(); // # second = row
            mColorVal = scan.nextInt(); // #  third = max color val
        }
        catch (Throwable t) {
            throw new ImageEditorException("invalid ppm file parameters");
        }
    }

    /**
     * initializes imgMap, gets the color information from a ppm file and puts it into 
     * imgMap color val 2D array. Throws an exception
     * if header values of the file don't match the given ppm file array
     */
    public void getPPMColorArray(Scanner scan) throws ImageEditorException {
        imgMap = new int[row][]; // recreates image color value array
        for (int i = 0; i<row; i++) {
            int[] inCol = new int[col*NUM_OF_COLORS]; // creates row/line of image color val array
            for (int j = 0; j<col; j++) {
                int r = -1; int g = -1; int b = -1; // initializes invalid color vals
                try {
                    r = scan.nextInt(); g = scan.nextInt(); b = scan.nextInt(); // attempts to get actual color vals
                }
                catch (Exception e) {throw new ImageEditorException("invalid row/col ppm array");}
                if (r==-1||g==-1||b==-1) { // if scanner can't find new color vals when header specified there was
                    System.out.println("invalid row/col ppm array");
                    throw new ImageEditorException("invalid row/col ppm array");
                }
                inCol[j*NUM_OF_COLORS] = r; // array size is col*number of colors, then addition of 1 and 2 for green and blue
                inCol[j*NUM_OF_COLORS+1] = g;
                inCol[j*NUM_OF_COLORS+2] = b;
            }
            try {
                imgMap[i] = inCol;
            }
            catch (Exception e) {
                System.out.println("invalid ppm file color array");
                throw new ImageEditorException();
            }
        }
    }

    /**
     * reads a ppm value. scans format, col/rows, maxcolorvalue, and rgb array
     */
    public void readPPMFile() {
        try {
            FileReader reader = new FileReader(new File(input));
            Scanner scan = new Scanner(reader);
            getPPMFileHeaderParameters(scan); // gets all header params of ppm file, throws exception if something off
            getPPMColorArray(scan); // gets color values array, throws exception if invalid
            scan.close(); // close scanner
            convertToPixelArray();
        } catch (Throwable t) {
            System.out.println("Error Processing Files");
            System.out.println(t);
            if (t instanceof Exception)
                t.printStackTrace();
        }
    }

}
