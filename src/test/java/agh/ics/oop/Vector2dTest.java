package agh.ics.oop;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    final Vector2d v1 = new Vector2d(1,1);
    final Vector2d v2 = new Vector2d(-1,-1);
    final Vector2d v3 = new Vector2d(1,1);
    final Vector2d v4 = new Vector2d(-1,1);
    final Vector2d v5 = new Vector2d(1,-1);
    @Test
    void textEquals(){
        assertTrue(v1.equals(v3));
        assertFalse(v1.equals(v4));
        assertFalse(v1.equals(v5));
    }
    @Test
    void testToString(){
        assertEquals("(1,1)", v1.toString());
        assertEquals("(-1,-1)", v2.toString());
    }
    @Test
    void testPrecedes(){
        assertTrue(v2.precedes(v1));
        assertTrue(v4.precedes(v1));
        assertTrue(v1.precedes(v3));
        assertFalse(v1.precedes(v2));
    }
    @Test
    void testFollows(){
        assertTrue(v1.follow(v2));
        assertTrue(v1.follow(v4));
        assertTrue(v3.follow(v1));
        assertFalse(v2.follow(v1));
    }
    @Test
    void testUpperRight(){
        assertEquals(v1, v4.upperRight(v5));
    }
    @Test
    void testLowerLeft(){
        assertEquals(v2, v4.lowerLeft(v5));
    }
    @Test
    void testAdd(){
        assertEquals(new Vector2d(0,0), v1.add(v2));
        assertEquals(new Vector2d(0,2), v3.add(v4));
    }
    @Test
    void testSubtract(){
        assertEquals(new Vector2d(2,2), v1.subtract(v2));
        assertEquals(new Vector2d(2,0), v3.subtract(v4));
    }
    @Test
    void testOpposite(){
        assertEquals(v1, v2.opposite());
        assertEquals(v4, v5.opposite());
        assertEquals(v1.opposite(), v3.opposite());
    }

}
