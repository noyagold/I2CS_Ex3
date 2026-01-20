package assignments.Ex2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Index2DTest {
    private static Index2D p;

    @BeforeAll
    static void setup() {
        p = new Index2D(0, 0);
    }

    @Test
    //x negative
    public void testGetX() {
        Index2D z = new Index2D(-1, 4);
        assertEquals(-1, z.getX());
    }

    @Test
// getX = 0
    public void testGetX_Zero() {
        assertEquals(0, p.getX());
    }

    @Test
// getY = 0
    public void testGetY_Zero() {
        assertEquals(0, p.getY());
    }

    @Test
// normal distance
    public void testDistance2DNormal() {
        Index2D p1 = new Index2D(0, 0);
        Index2D p2 = new Index2D(3, 4);
        assertEquals(5.0, p1.distance2D(p2));
    }

    @Test
//  null
    public void testDistance2DNull() {
        try {
            p.distance2D(null);
            fail(); // must throw exception
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    // distance on Y axis
    @Test
    void testDistance2D_Y() {
        Index2D a = new Index2D(0, 4);
        assertEquals(4.0, p.distance2D(a));
    }

    // distance diagonal
    @Test
    void testDistance2D_Diagonal() {
        Index2D a = new Index2D(3, 4);
        assertEquals(5.0, p.distance2D(a), 0.001);
    }

    @Test
// toString normal
    public void testToStringNormal() {
        Index2D a = new Index2D(3, 5);
        assertEquals("(3,5)", a.toString());
    }

    @Test
// toString negative
    public void testToStringNegative() {
        Index2D a = new Index2D(-1, -2);
        assertEquals("(-1,-2)", a.toString());
    }

    @Test
// equals true cases
    public void testEqualsTrue() {
        Index2D a = new Index2D(2, 3);
        Index2D b = new Index2D(2, 3);
        assertTrue(a.equals(b));
        assertTrue(a.equals(a)); // same object
    }

    @Test
// equals false cases
    public void testEqualsFalse() {
        Index2D a = new Index2D(2, 3);
        Index2D b = new Index2D(3, 2);
        assertFalse(a.equals(b));     // different values
        assertFalse(a.equals(null));  // null
        assertFalse(a.equals("x"));   // different type
    }

}