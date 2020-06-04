package my_tetris.general_game_objects;

import my_tetris.Direction;
import my_tetris.Element;
import my_tetris.ElementsMatrix;
import my_tetris.events.GlassEvent;
import my_tetris.events.GlassListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SinkingShapeTest {

    SinkingShape shape;
    
    @BeforeEach
    void setUp() {

        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(2, 1)), 1);
        shapeElements.add(new Element(new Point(2, 2)), 2);
        shapeElements.add(new Element(new Point(1, 2)), 2);
        shape = new SinkingShape(2, shapeElements);
        

    }
    
    @Test
    void sink_afterMoving() {

        TestGlassListener glassObs = new TestGlassListener();
        var glass = new Glass(10, 10);        
        shape.setGlass(glass);

        //заполнить стакан 10-ю элементами
        for (int i = 3; i < 8; i++) {
            glass.add(new Element(i, 8));
            glass.add(new Element(i, 7));
        }
        
        glass.addGlassListener(glassObs);
        shape.move(Direction.SOUTH);
        shape.move(Direction.SOUTH);
        shape.move(Direction.SOUTH);
        
        assertTrue(glass.elementsCount(9) == 2);
        assertTrue(glass.elementsCount(8) == 5);
        assertTrue(glass.elementsCount(7) == 5);
        assertTrue(glassObs.callsCount == 4);
    }

    @Test
    void sink_afterRotation() {

        TestGlassListener glassObs = new TestGlassListener();
        var glass = new Glass(10, 10);
        shape.setGlass(glass);

        //заполнить стакан 10-ю элементами
        for (int i = 3; i < 8; i++) {
            glass.add(new Element(i, 8));
            glass.add(new Element(i, 7));
        }

        glass.addGlassListener(glassObs);
        shape.rotate();
        shape.move(Direction.SOUTH);
        shape.rotate();
        shape.move(Direction.SOUTH);
        shape.move(Direction.SOUTH);
        
        assertTrue(glass.elementsCount(9) == 1);
        assertTrue(glass.elementsCount(8) == 4);
        assertTrue(glass.elementsCount(7) == 5);
        assertTrue(glassObs.callsCount == 4);
    }
    
    @Test
    void canRotate_sinkingStarted() {
        
        shape.currentDepth = 1;
        assertFalse(shape.canRotate());        
    }
    
    @Test
    void rotateElements() {
    }

    @Test
    void canMove_NotSouthAndSinkingStarted() {

        shape.currentDepth = 1;
        
        assertFalse(shape.canMove(Direction.EAST));
    }

    @Test
    void canMove_SinkingFinished() {

        shape.currentDepth = 2;

        assertFalse(shape.canMove(Direction.SOUTH));
    }
    
    @Test
    void canMove_throughGlassElements() {

        var glass = new Glass(10, 10);
        shape.setGlass(glass);

        //заполнить стакан 10-ю элементами
        for (int i = 3; i < 8; i++) {
            glass.add(new Element(6, 8));
            glass.add(new Element(6, 7));
        }

        assertTrue(shape.canMove(Direction.SOUTH));
    }
    
    @Test
    void moveElements() {
        
        
    }

    @Test
    void isCorrectPosition_correct() {
        
        var glass = new Glass(10, 10);
        shape.setGlass(glass);
        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(2, 1)), 1);
        shapeElements.add(new Element(new Point(2, 2)), 2);
        shapeElements.add(new Element(new Point(1, 2)), 2);
        
        assertTrue(shape.isCorrectPosition(shapeElements));
    }

    @Test
    void isCorrectPosition_incorrect() {

        var glass = new Glass(10, 10);
        shape.setGlass(glass);
        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(10, 0)), 0);
        shapeElements.add(new Element(new Point(2, 1)), 1);
        shapeElements.add(new Element(new Point(2, 2)), 2);
        shapeElements.add(new Element(new Point(1, 2)), 2);

        assertFalse(shape.isCorrectPosition(shapeElements));
    }

    private class TestGlassListener implements GlassListener {

        public int callsCount = 0;

        @Override
        public void glassFilled() { callsCount++; }

        @Override
        public void needNewActiveShape() { callsCount++; }

        @Override
        public void shapeAbsorbed() { callsCount++; }

        @Override
        public void rowCleared(GlassEvent e) { callsCount++; }

        @Override
        public void glassContentChanged() { callsCount++; }
    }
}