package assignments.Ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Intro2CS, 2026A, this is a very.
 */
class MapTest {
    /**
     */
    private int[][] map_m0 = {{0,1,2}, {5,6,7}, {10,11,12}};
    private int[][] map_m1 = {{0,1,2}, {5,6,7}, {10,11,12}};
    private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private int[][] map_one = {{0}};
    private int[][] map_vals = {{1, 2, 3}, {4, 5, 6}};
    private int[][] map_obs = {{0, -1, 0}, {0, -1, 0}, {0,  0, 0}};
    private int[][] map_null = null;
    private int[][] map_empty_row = {{}};

    private Map2D _m0, _m1, _m3_3;
    private Map2D _one, _vals, _obs;
    @BeforeEach
    public void setup() {
        _m0 = new Map(map_m0);
        _m1 = new Map(map_m1);
        _m3_3 = new Map(_map_3_3);
        _one = new Map(map_one);
        _vals = new Map(map_vals);
        _obs = new Map(map_obs);


    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [500][500];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        _m1.fill(p1,1, true);
    }

    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }
    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
    }

    //should throw RuntimeException on null input.
    @Test
    void testInit_NullArray() {
        assertThrows(RuntimeException.class, () -> {
            _m0.init(map_null);
        });
    }

    //should throw RuntimeException on empty row.
    @Test
    void testInit_EmptyRow() {
        assertThrows(RuntimeException.class, () -> {
            _m0.init(map_empty_row);
        });
    }


    //same Diminutions
    @Test
    void testGetMap() {
        int[][] a = _m3_3.getMap();
        assertEquals(3, a.length);
        assertEquals(3, a[0].length);
    }


    @Test
    void testGetMap_DeepCopy() {
        int[][] copy = _m0.getMap();
        _m0.setPixel(0, 0, 999);// change the original map
        assertEquals(0, copy[0][0]); // copy must stay with the old value
        assertEquals(999, _m0.getPixel(0, 0)); // and original changed
    }


        @Test
    void testGetWidth() {
            assertEquals(3, _m3_3.getWidth());
            assertEquals(3, _m0.getWidth());
            assertEquals(1, _one.getWidth());
            assertEquals(3, _obs.getWidth());
            assertEquals(3, _vals.getWidth());
    }

    @Test
    void testGetHeight() {
        assertEquals(3, _m3_3.getHeight());
        assertEquals(3, _m0.getHeight());
        assertEquals(1, _one.getHeight());
        assertEquals(3, _obs.getHeight());
        assertEquals(2, _vals.getHeight());
    }

    @Test
    void testGetPixelXY() {
        assertEquals(1, _m3_3.getPixel(1,0));
        assertEquals(0, _m0.getPixel(0, 0));
        assertEquals(7, _m0.getPixel(2, 1));
        assertEquals(0, _one.getPixel(0, 0));
        assertEquals(-1, _obs.getPixel(1, 0));
        assertEquals(0, _obs.getPixel(0, 2));
    }

    @Test
    void testGetPixelP() {
        Pixel2D p1 = new Index2D(1, 0);
        Pixel2D p2 = new Index2D(0, 1);
        assertEquals(1, _m3_3.getPixel(p1));
        assertEquals(1, _m3_3.getPixel(p2));
        Pixel2D p3 = new Index2D(2, 1);
        assertEquals(7, _m0.getPixel(p3));
        Pixel2D p4 = new Index2D(0, 0);
        assertEquals(0, _one.getPixel(p4));
        Pixel2D p5 = new Index2D(1, 0);
        assertEquals(-1, _obs.getPixel(p5));
    }

        @Test
        void testSetPixelXY() {
            _m3_3.setPixel(1, 0, 9);
            assertEquals(9, _m3_3.getPixel(1, 0));
            _m0.setPixel(2, 1, 99);
            assertEquals(99, _m0.getPixel(2, 1));
            _one.setPixel(0, 0, 7);
            assertEquals(7, _one.getPixel(0, 0));
            _obs.setPixel(1, 0, 7);
            assertEquals(7, _obs.getPixel(1, 0));
        }

    @Test
    void testSetPixelP() {
        Pixel2D p1 = new Index2D(1, 0);
        _m3_3.setPixel(p1, 8);
        assertEquals(8, _m3_3.getPixel(p1));
        Pixel2D p2 = new Index2D(2, 1);
        _m0.setPixel(p2, 4);
        assertEquals(4, _m0.getPixel(p2));
        Pixel2D p3 = new Index2D(0, 0);
        _one.setPixel(p3, -1);
        assertEquals(-1, _one.getPixel(p3));
        Pixel2D p4 = new Index2D(1, 1);
        _obs.setPixel(p4, 6);
        assertEquals(6, _obs.getPixel(p4));}

    @Test
    void testIsInsideTrue() {
        assertTrue(_m3_3.isInside(new Index2D(0, 0)));
        assertTrue(_m0.isInside(new Index2D(1, 1)));
        assertTrue(_one.isInside(new Index2D(0, 0)));
        assertTrue(_obs.isInside(new Index2D(2, 2)));
    }

    @Test
    void testIsInsideFalse() {
        assertFalse(_m3_3.isInside(new Index2D(-1, 0)));
        assertFalse(_m3_3.isInside(new Index2D(0, -1)));
        assertFalse(_m0.isInside(new Index2D(5, 5)));
        assertFalse(_one.isInside(new Index2D(1, 0)));
        assertFalse(_one.isInside(new Index2D(0, 1)));
    }

    @Test
    void testSameDimensionsTrue() {
        assertTrue(_m0.sameDimensions(_m1));
        assertTrue(_m1.sameDimensions(_m0));
        Map2D a = new Map(_map_3_3);
        assertTrue(_m3_3.sameDimensions(a));
    }

    @Test
    void testSameDimensionsFalse() {
        assertFalse(_m0.sameDimensions(_one));
        assertFalse(_one.sameDimensions(_vals));
        assertFalse(_vals.sameDimensions(_obs));
    }

    @Test
    void testAddMap2D() {
        _m0.addMap2D(_m1);
        assertEquals(2, _m0.getPixel(1,0));
    }

    @Test
    void testAddMap2D_DifferentSize() {
        Map2D before = new Map(map_m0);
        _m0.addMap2D(_vals);
        assertEquals(before, _m0);
    }

    @Test
    void testMul() {
        _m0.mul(2);
        assertEquals(2, _m0.getPixel(1,0));
    }

    @Test
    void testRescale() {
        _m3_3.rescale(2, 1);
        assertEquals(6, _m3_3.getWidth());
        assertEquals(3, _m3_3.getHeight());
        _m3_3 = new Map(_map_3_3); // reset to 3x3
        _m3_3.rescale(0.5, 0.5);//smaller
        assertEquals(1, _m3_3.getWidth());
        assertEquals(1, _m3_3.getHeight());
    }

    @Test
    void testDrawCircle() {
        _m0.init(3,3,0);
        _m0.drawCircle(new Index2D(1,1), 1.1, 5);
        assertEquals(5, _m0.getPixel(1,1));}


    @Test
    void testDrawLine() {
        _m0.init(3,3,0);
        _m0.drawLine(new Index2D(0,1), new Index2D(2,1), 9);
        assertEquals(9, _m0.getPixel(0,1));
        assertEquals(9, _m0.getPixel(2,1));
    }

    @Test
    void testDrawRect() {
        _m3_3.drawRect(new Index2D(0,0), new Index2D(1,1), 7);
        assertEquals(7, _m3_3.getPixel(0,0));
        assertEquals(7, _m3_3.getPixel(1,1));
    }


        @Test
        void testFill() {
            _m3_3.init(_map_3_3);
            int filled1 = _m3_3.fill(new Index2D(0,0), 5, false);
            assertEquals(1, filled1);
            assertEquals(5, _m3_3.getPixel(0,0));
            int filled2 = _m3_3.fill(new Index2D(1,0), 7, false);
            assertEquals(1, filled2);
            assertEquals(7, _m3_3.getPixel(1,0));
            int filled3 = _m3_3.fill(new Index2D(1,1), 9, false);
            assertEquals(1, filled3);
            assertEquals(9, _m3_3.getPixel(1,1));
        }

    @Test
    void testShortestPath() {
        Pixel2D start = new Index2D(0, 0);
        Pixel2D end   = new Index2D(2, 0);
        Pixel2D[] path = _obs.shortestPath(start, end, -1, false);
        assertNotNull(path);
        assertEquals(start, path[0]);
        assertEquals(end, path[path.length - 1]);
        for (Pixel2D p : path) {
            assertNotEquals(-1, _obs.getPixel(p));}
    }

    @Test
    void testAllDistanceWithObstacles() {
        Map2D m = new Map(new int[][]{
                {0, -1, 0, 0},
                {0, -1, 0, -1},
                {0,  0, 0,  0}
        });

        Pixel2D start = new Index2D(0, 0);
        Map2D dist = m.allDistance(start, -1, false);

        assertEquals(0, dist.getPixel(0, 0)); // start
        assertEquals(1, dist.getPixel(0, 1));
        assertEquals(2, dist.getPixel(0, 2));
        assertEquals(3, dist.getPixel(1, 2));
        assertEquals(4, dist.getPixel(2, 2));
        assertEquals(-1, dist.getPixel(1, 0)); // obstacle
    }


    }



















