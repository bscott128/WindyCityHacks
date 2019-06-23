/**
 * A pixel object, has r, g, and b integer values for color.
 *
 * @author Mark W
 * @version final
 */
public class Pixel
{
    private int r,g,b;

    public float gray() {
        int solid = (r + g + b)/3;
        return solid/255.0f;
    }

    public int gR() {
        return r;   
    }

    public int gG() {
        return g;   
    }

    public int gB() {
        return b;   
    }

    /**
     * gets the sum of color values
     */
    public int getCSum() {
        return (r+g+b);   
    }

    /**
     * remove a particular color from pixel
     * 0b100 = red
     * 0b010 = green
     * 0b001 = blue
     */
    public void removeC(int i) {
        int rM = 0b100;
        int gM = 0b010;
        int bM = 0b001;
        if (i==rM) {
            r = 0;   
        }
        else if (i==gM) {
            g = 0;   
        }
        else if (i==bM) {
            b = 0;   
        }
        else if (i==0b000) {
            r = 0;
            g = 0;
            b = 0;
        }
    }

    /**
     * multiplies all rgb values by a ratio (percentage
     * e.g. 76 = .76 ratio
     */
    public void alterCVals(int percent) {
        double ratio = percent/100.0;
        r*=ratio;
        g*=ratio;
        b*=ratio;
        if (r<0) { // makes sure color val < 0
            r = 0;   
        }
        if (g<0) {
            g = 0;   
        }
        if (b<0) {
            b = 0;   
        }
    }

    /**
     * Constructor for objects of class Pixel
     */
    public Pixel(int red, int green, int blue)
    {
        r = red;
        g = green;
        b = blue;
    }
}
