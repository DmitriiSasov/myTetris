package my_tetris.general_game_objects;

import my_tetris.Direction;
import my_tetris.Element;
import my_tetris.ElementsMatrix;
import my_tetris.events.GlassEvent;
import my_tetris.events.GlassListener;
import my_tetris.events.ShapeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Iterator;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class AbstractShapeTest {

    private AbstractShape shape;

    private boolean equalPositions(AbstractShape other) {

        if (shape.elementsCount() != other.elementsCount()) {
            return false;
        }

        Iterator<Element> iterator = other.allElementsConstIterator();
        Iterator<Element> iterator2 = shape.allElementsConstIterator();
        while(iterator.hasNext()) {

            if (!iterator.next().equals(iterator2.next())) {
                return false;
            }
        }

        return true;
    }

    @BeforeEach
    void setUp() {

        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(2, 0)), 0);
        shapeElements.add(new Element(new Point(2, 1)), 1);
        shapeElements.add(new Element(new Point(2, 2)), 2);
        shapeElements.add(new Element(new Point(1, 2)), 2);
        shape = new Shape(shapeElements);
        
    }


    @Test
    void setGlass_shapeWithoutGlass() {

        var glass = new Glass(10,20);
        shape.setGlass(glass);
        boolean result = shape.getGlass() == glass;
        result = result && glass.getActiveShape() == shape;

        assertTrue(result);
    }

    @Test
    void setGlass_shapeWithGlass() {

        var glass = new Glass(10,20);
        shape.setGlass(glass);
        var glass_2 = new Glass(15,15);
        shape.setGlass(glass_2);
        boolean result = shape.getGlass() == glass_2;
        result = result && glass_2.getActiveShape() == shape && shape.getGlass() == glass_2
                && glass.getActiveShape() == null;

        assertTrue(result);
    }

    @Test
    void unsetGlass_shapeWithoutGlass() {

        shape.unsetGlass();
        boolean result = shape.getGlass() == null;

        assertTrue(result);
    }

    @Test
    void unsetGlass_shapeWithGlass() {

        var glass = new Glass(10,10);
        shape.setGlass(glass);
        shape.unsetGlass();
        boolean result = shape.getGlass() == null && glass.getActiveShape() == null;

        assertTrue(result);
    }

    
    @Test
    void rotate_yes() {

        TestShapeObserver obs = new TestShapeObserver();
        shape.addShapeListener(obs);
        var glass = new Glass(10,10);
        shape.setGlass(glass);
        shape.rotate();

        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(5, 10)), 0);
        shapeElements.add(new Element(new Point(6, 10)), 1);
        shapeElements.add(new Element(new Point(7, 10)), 2);
        shapeElements.add(new Element(new Point(7, 11)), 2);
        var expectedShape = new Shape(shapeElements);

        assertTrue(equalPositions(expectedShape));
        assertTrue(obs.callsCount == 3);
    }

    @Test
    void rotate_elementOutsideBorder() {

        
        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(5, 0)), 0);
        shapeElements.add(new Element(new Point(5, 1)), 1);
        shapeElements.add(new Element(new Point(5, 2)), 2);
        shapeElements.add(new Element(new Point(4, 2)), 2);
        shape = new Shape(shapeElements);
        TestShapeObserver obs = new TestShapeObserver();
        shape.addShapeListener(obs);
        var glass = new Glass(10,10);
        shape.setGlass(glass);
        shape.rotate();

        shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(9, 9)), 0);
        shapeElements.add(new Element(new Point(9, 10)), 1);
        shapeElements.add(new Element(new Point(9, 11)), 2);
        shapeElements.add(new Element(new Point(8, 11)), 2);
        var expectedShape = new Shape(shapeElements);

        assertTrue(equalPositions(expectedShape));
        assertTrue(obs.callsCount == 2);
    }

    @Test
    void rotate_elementPrevents() {

        TestShapeObserver obs = new TestShapeObserver();
        shape.addShapeListener(obs);
        var glass = new Glass(10,10);
        shape.setGlass(glass);
        glass.add(new Element(7, 9));
        shape.move(Direction.SOUTH);
        shape.rotate();

        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(6, 8)), 0);
        shapeElements.add(new Element(new Point(6, 9)), 1);
        shapeElements.add(new Element(new Point(6, 10)), 2);
        shapeElements.add(new Element(new Point(5, 10)), 2);
        var expectedShape = new Shape(shapeElements);

        assertTrue(equalPositions(expectedShape));
        assertTrue(obs.callsCount == 3);
    }
    
    @Test
    void setStartPosition_tooLongRow() {

        TestShapeObserver obs = new TestShapeObserver();
        shape.addShapeListener(obs);
        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(5, 0)), 0);
        for (int i = 0; i < 9; i++) {
            
            shapeElements.add(new Element(new Point(i, 1)), 1);
        }        
        shapeElements.add(new Element(new Point(5, 2)), 2);
        shapeElements.add(new Element(new Point(4, 2)), 2);
        shape = new Shape(shapeElements);
        
        try {

            shape.setStartPosition(0, 7, 0, 7);
            assertTrue(false);
        } catch (AbstractShape.ShapeStartPositionException ex) { assertTrue(true);}

        assertTrue(obs.callsCount == 0);
    }

    @Test
    void setStartPosition_elementsOutsideBorder() {

        TestShapeObserver obs = new TestShapeObserver();
        shape.addShapeListener(obs);
        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(5, 0)), 0);
        shapeElements.add(new Element(new Point(5, 1)), 1);        
        shapeElements.add(new Element(new Point(5, 2)), 2);
        shapeElements.add(new Element(new Point(4, 2)), 2);
        shape = new Shape(shapeElements);

        try {

            shape.setStartPosition(0, 4, 0, 4);
            assertTrue(false);
        } catch (AbstractShape.ShapeStartPositionException ex) { assertTrue(true);}
        assertTrue(obs.callsCount == 0);
    }

    @Test
    void setStartPosition_tooManyRows() {

        TestShapeObserver obs = new TestShapeObserver();
        shape.addShapeListener(obs);
        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(5, 0)), 0);
        shapeElements.add(new Element(new Point(5, 1)), 1);
        shapeElements.add(new Element(new Point(5, 2)), 2);
        shapeElements.add(new Element(new Point(4, 2)), 2);
        shape = new Shape(shapeElements);

        try {

            shape.setStartPosition(0, 3, 0, 4);
            assertTrue(false);
        } catch (AbstractShape.ShapeStartPositionException ex) { assertTrue(true);}
        assertTrue(obs.callsCount == 0);
    }
  
    @Test
    void move_down() {

        TestShapeObserver obs = new TestShapeObserver();
        shape.addShapeListener(obs);
        var glass = new Glass(10,10);
        shape.setGlass(glass);
        shape.move(Direction.SOUTH);

        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(6, 8)), 0);
        shapeElements.add(new Element(new Point(6, 9)), 1);
        shapeElements.add(new Element(new Point(6, 10)), 2);
        shapeElements.add(new Element(new Point(5, 10)), 2);
        var expectedShape = new Shape(shapeElements);

        assertTrue(equalPositions(expectedShape));
        assertTrue(obs.callsCount == 3);
    }
 
    @Test
    void move_borderRight() {

        TestShapeObserver obs = new TestShapeObserver();
        shape.addShapeListener(obs);
        var glass = new Glass(10,10);
        shape.setGlass(glass);

        for (int i = 0; i < 10; i++) { shape.move(Direction.EAST); }
        

        ElementsMatrix shapeElements = new ElementsMatrix(3);
        shapeElements.add(new Element(new Point(9, 9)), 0);
        shapeElements.add(new Element(new Point(9, 10)), 1);
        shapeElements.add(new Element(new Point(9, 11)), 2);
        shapeElements.add(new Element(new Point(8, 11)), 2);
        var expectedShape = new Shape(shapeElements);

        assertTrue(equalPositions(expectedShape));
        assertTrue(obs.callsCount == 5);
    }

    @Test
    void move_elementBelow() {

        TestGlassListener glassObs = new TestGlassListener();
        TestShapeObserver shapeObs = new TestShapeObserver();
        shape.addShapeListener(shapeObs);
        var glass = new Glass(10,10);
        glass.addGlassListener(glassObs);
        shape.setGlass(glass);
        glass.add(new Element(6, 8));
        shape.move(Direction.SOUTH);

        ElementsMatrix shapeElements = new ElementsMatrix(3);
        var expectedShape = new Shape(shapeElements);

        assertTrue(equalPositions(expectedShape));
        assertTrue(glassObs.callsCount == 4);
        assertTrue(shapeObs.callsCount == 3);
    }
    
    private class TestShapeObserver implements ShapeListener {

        public int callsCount = 0;
        
        @Override
        public void shapeReadyToMove() { callsCount++; }

        @Override
        public void shapeLocationChanged(){ callsCount++; }

        @Override
        public void shapeStopped() { callsCount++; }
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