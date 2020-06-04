package my_tetris;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ElementTest {

    /**
     * Test of getCol method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testElementConstructor_1() {
        System.out.println("ElementConstructor_1");

        Element instance = new Element(new Point(1,3));
        int[] expResult = {1,3};
        int[] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getCol method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testElementConstructor_2() {
        System.out.println("ElementConstructor_2");

        Element instance = new Element(-1, 3);
        int[] expResult = {-1, 3};
        int[] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getCol method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testGetCol() {
        System.out.println("getCol");

        Element instance = new Element(-1, 3);
        int expResult = -1;
        int result = instance.getCol();

        assertEquals(expResult, result);
    }

    /**
     * Test of getRow method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testGetRow() {
        System.out.println("getRow");

        Element instance = new Element(-1, 3);
        int expResult = 3;
        int result = instance.getRow();

        assertEquals(expResult, result);
    }

    /**
     * Test of setPosition method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testSetPosition() {
        System.out.println("setPosition");

        Element instance = new Element(-1, 3);
        int[] expResult = {2, -10};
        instance.setPosition(2, -10);
        int [] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of move method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testMoveUp() {
        System.out.println("move");

        Element instance = new Element(-1, 3);
        int[] expResult = {-1, 4};
        instance.move(Direction.NORTH);
        int [] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of move method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testMoveDown() {
        System.out.println("move");

        Element instance = new Element(-1, 3);
        int[] expResult = {-1, 2};
        instance.move(Direction.SOUTH);
        int [] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of move method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testMoveRight() {
        System.out.println("move");

        Element instance = new Element(-1, 3);
        int[] expResult = {0, 3};
        instance.move(Direction.EAST);
        int [] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of move method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testMoveLeft() {
        System.out.println("move");

        Element instance = new Element(-1, 3);
        int[] expResult = {-2, 3};
        instance.move(Direction.WEST);
        int [] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of rotate method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testRotate_rotateRotationCenter() {
        System.out.println("rotate");

        Element instance = new Element(-1, 3);
        Element rotationCenter = new Element(-1, 3); 
        int[] expResult = {-1, 3};
        instance.rotate(rotationCenter);
        int [] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of rotate method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testRotate() {
        System.out.println("rotate");

        Element instance = new Element(-1, 3);
        Element rotationCenter = new Element(1, 4);
        int[] expResult = {0, 6};
        instance.rotate(rotationCenter);
        int [] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of clone method, of class Element.
     */
    @org.junit.jupiter.api.Test
    public void testClone() {
        System.out.println("clone");

        Element instance = new Element(-1, 3);
        int[] expResult = {-1, 3};
        Element clone = null;
        try { 
         
            clone = instance.clone();
            clone.move(Direction.SOUTH);
        } 
        catch (CloneNotSupportedException e) { e.printStackTrace(); }
        int [] result = {instance.getCol(), instance.getRow()};

        assertArrayEquals(expResult, result);
    }
}