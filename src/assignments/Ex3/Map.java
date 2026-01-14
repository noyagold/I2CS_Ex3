package assignments.Ex3;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D {
    private int[][] _map;
    private boolean _cyclicFlag = true;

    /**
     * Constructs a w*h 2D raster map with an init value v.
     * @param w
     * @param h
     * @param v
     */
    public Map(int w, int h, int v) {init(w,h, v);}
    /**
     * Constructs a square map (size*size).
     * @param size
     */
    public Map(int size) {this(size,size, 0);}

    /**
     * Constructs a map from a given 2D array.
     * @param data
     */
    public Map(int[][] data) {
        init(data);
    }
    @Override
    public void init(int w, int h, int v) {
        /////// add your code below ///////
        _map = new int[w][h];
        for (int x = 0; x < w; x++) for (int y = 0; y < h; y++) _map[x][y] = v;
        ///////////////////////////////////
    }
    @Override
    public void init(int[][] arr) {
        /////// add your code below ///////
        _map = new int[arr.length][arr[0].length];
        for (int x = 0; x < arr.length; x++) System.arraycopy(arr[x], 0, _map[x], 0, arr[0].length);

        ///////////////////////////////////
    }
    @Override
    public int[][] getMap() {

        /////// add your code below ///////
        return _map;
        ///////////////////////////////////

    }
    @Override
    /////// add your code below ///////
    public int getWidth() {return _map.length; }
    @Override
    /////// add your code below ///////
    public int getHeight() {return _map[0].length;}
    @Override
    /////// add your code below ///////
    public int getPixel(int x, int y) { return _map[x][y]; }
    @Override
    /////// add your code below ///////
    public int getPixel(Pixel2D p) {
        return this.getPixel(p.getX(),p.getY());
    }
    @Override
    /////// add your code below ///////
    public void setPixel(int x, int y, int v) {_map[x][y] = v; }
    @Override
    /////// add your code below ///////
    public void setPixel(Pixel2D p, int v) {
        setPixel(p.getX(), p.getY(), v);
    }
    @Override
    /**
     * Fills this map with the new color (new_v) starting from p.
     * https://en.wikipedia.org/wiki/Flood_fill
     */
    public int fill(Pixel2D xy, int new_v) {
        int ans=0;
        /////// add your code below ///////

        ///////////////////////////////////
        return ans;
    }

    @Override
    /**
     * BFS like shortest the computation based on iterative raster implementation of BFS, see:
     * https://en.wikipedia.org/wiki/Breadth-first_search
     */
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
        Pixel2D[] ans = null;  // the result.
        /////// add your code below ///////

        ///////////////////////////////////
        return ans;
    }
    @Override
    /////// add your code below ///////
    public boolean isInside(Pixel2D p) {
        return false;
    }

    @Override
    /////// add your code below ///////
    public boolean isCyclic() {
        return false;
    }
    @Override
    /////// add your code below ///////
    public void setCyclic(boolean cy) {;}
    @Override
    /////// add your code below ///////
    public Map2D allDistance(Pixel2D start, int obsColor) {
        Map2D ans = null;  // the result.
        /////// add your code below ///////

        ///////////////////////////////////
        return ans;
    }
}