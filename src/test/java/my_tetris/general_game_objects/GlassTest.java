package my_tetris.general_game_objects;

import my_tetris.Element;
import my_tetris.ElementsMatrix;
import my_tetris.ImmutableElementMatrix;
import my_tetris.ShapeFactory;
import my_tetris.events.GlassEvent;
import my_tetris.events.GlassListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


class GlassTest {

    private Glass glass;

    private boolean equals(ImmutableElementMatrix em1, ImmutableElementMatrix em2) {

        if (em1 == em2) {
            return true;
        }
        if (em1.elementsCount() != em2.elementsCount()) {
            return false;
        }

        Iterator<Element> iterator1 = em1.allElementsConstIterator();
        Iterator<Element> iterator2 = em2.allElementsConstIterator();

        while (iterator1.hasNext()) {

            if (!iterator1.next().equals(iterator2.next())) {

                return false;
            }
        }
        return true;
    }
    
    @BeforeEach
    void setUp() {

        glass = new Glass(10,10);
    }

    @Test
    void constructor_smallBorder() {

        try {

            glass = new Glass(5,5);
            assertTrue(false);

        } catch(RuntimeException ex) {

            assertTrue(true);
        }
    }

    @Test
    void setActiveShape_elementsOnGlassTop() {
        
        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(2,9);
        Element e2 = new Element(3,8);
        Element e3 = new Element(4,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        AbstractShape shape = new Shape(instance);
        instance = new ElementsMatrix(5);
        e = new Element(5,19);
        e1 = new Element(6,18);
        e2 = new Element(7,17);
        e3 = new Element(8,16);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        AbstractShape expectedShape = new Shape(instance);
        
        
        glass.setActiveShape(shape);
        
        assertTrue(glass.getActiveShape() == shape);
        assertTrue(shape.getGlass() == glass);
        assertTrue(equals(shape, expectedShape));
    }

    @Test
    void setActiveShape_elementsOutsideBorder() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(7,10);
        Element e1 = new Element(5,9);
        Element e2 = new Element(3,8);
        Element e3 = new Element(4,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        AbstractShape shape = new Shape(instance);

        try {
            
            glass.setActiveShape(shape);
            assertTrue(false);
        }
        catch (AbstractShape.ShapeStartPositionException ex) { assertTrue(true);}
    }

    @Test
    void setActiveShape_thereIsShape() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(0,10);
        Element e1 = new Element(0,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        AbstractShape shape = new Shape(instance);
        instance = new ElementsMatrix(5);
        e = new Element(8,28);
        e1 = new Element(8,27);
        e2 = new Element(9,26);
        e3 = new Element(9,25);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        AbstractShape expectedShape = new Shape(instance);


        glass.setActiveShape(shape);
        AbstractShape shapeClone = shape.clone();
        glass.setActiveShape(shapeClone);

        assertTrue(glass.getActiveShape() == shapeClone);
        assertTrue(shapeClone.getGlass() == glass);
        assertTrue(shape.getGlass() == null);
        assertTrue(equals(shapeClone, expectedShape));

    }

    @Test
    void unsetActiveShape_noShape() {

        glass.unsetActiveShape();
        boolean result = glass.getActiveShape() == null;

        assertTrue(result);
    }

    @Test
    void unsetActiveShape_thereIsShape() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(0,10);
        Element e1 = new Element(0,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        AbstractShape shape = new Shape(instance);
        glass.setActiveShape(shape);

        glass.unsetActiveShape();
        boolean result = glass.getActiveShape() == null && shape.getGlass() == null;

        assertTrue(result);
    }

    @Test
    void add_oneRowCleared() {

        var obs = new TestGlassObserver();
        glass.addGlassListener(obs);
        ArrayList<Element> elementsRow1 = new ArrayList<>();
        ArrayList<Element> elementsRow2 = new ArrayList<>();
        ArrayList<Element> elementsRow3 = new ArrayList<>();

        for (int j = 0; j < glass.getColCount(); j++) {

            elementsRow1.add(new Element(j, 0));
        }
        for (int j = 0; j < glass.getColCount() / 2; j++) {

            elementsRow2.add(new Element(j, 1));
        }
        elementsRow3.add(new Element(5, 2));

        glass.add(elementsRow3);
        glass.add(elementsRow2);
        glass.add(elementsRow1);

        assertTrue(glass.elementsCount() == 6);
        assertTrue(glass.elementsCount(0) == 5);
        assertTrue(glass.elementsCount(1) == 1);
        assertTrue(obs.callsCount == 7);
    }

    @Test
    void add_twoRowsCleared() {

        var obs = new TestGlassObserver();
        glass.addGlassListener(obs);
        ElementsMatrix instance = new ElementsMatrix(5);
        ArrayList<Element> elementsRow1 = new ArrayList<>();
        ArrayList<Element> elementsRow2 = new ArrayList<>();
        ArrayList<Element> elementsRow3 = new ArrayList<>();
    
        for (int j = 0; j < glass.getColCount(); j++) {

            elementsRow1.add(new Element(j, 0));
        }
        for (int j = 0; j < glass.getColCount(); j++) {

            elementsRow2.add(new Element(j, 1));
        }
        elementsRow3.add(new Element(5, 2));
        instance.add(elementsRow1, 0);
        instance.add(elementsRow2, 1);
        instance.add(elementsRow3, 2);
        Element expectedResult = new Element(5, 0);
        
        glass.add(instance);
        
        assertTrue(glass.elementsCount() == 1);
        assertTrue(glass.elementsCount(0) == 1);
        assertTrue(expectedResult.equals(glass.getElement(0, 0)));
        assertTrue(obs.callsCount == 4);
    }

    @Test
    void add_zeroRowCleared() {

        var obs = new TestGlassObserver();
        glass.addGlassListener(obs);
        ArrayList<Element> elementsRow1 = new ArrayList<>();
        ArrayList<Element> elementsRow2 = new ArrayList<>();

        for (int j = 0; j < glass.getColCount() - 1; j++) {

            elementsRow1.add(new Element(j, 0));
        }
        for (int j = 0; j < glass.getColCount() / 2; j++) {

            elementsRow2.add(new Element(j, 1));
        }

        glass.add(elementsRow1);
        glass.add(elementsRow2);
        glass.add(new Element(5, 2));

        assertTrue(glass.elementsCount() == 15);
        assertTrue(glass.elementsCount(0) == 9);
        assertTrue(glass.elementsCount(1) == 5);
        assertTrue(glass.elementsCount(2) == 1);
        assertTrue(obs.callsCount == 6);
    }

    @Test
    void add_GlassFilled() {

        var obs = new TestGlassObserver();
        glass.addGlassListener(obs);
        ArrayList<Element> elementsRow1 = new ArrayList<>();
        
        for (int j = 0; j < glass.getColCount() - 1; j++) {

            elementsRow1.add(new Element(j, glass.getRowCount() - 1));
        }
        glass.add(elementsRow1);

        assertTrue(glass.elementsCount() == 9);
        assertTrue(glass.elementsCount(glass.getRowCount() - 1) == 9);
        assertTrue(obs.callsCount == 2);
    }

    @Test
    void add_matrixesSizeDifferent() {

        var obs = new TestGlassObserver();
        glass.addGlassListener(obs);
        ElementsMatrix instance = new ElementsMatrix(11);
        ArrayList<Element> elementsRow1 = new ArrayList<>();
        ArrayList<Element> elementsRow2 = new ArrayList<>();
        ArrayList<Element> elementsRow3 = new ArrayList<>();

        for (int j = 0; j < glass.getColCount(); j++) {

            elementsRow1.add(new Element(j, 0));
        }
        for (int j = 0; j < glass.getColCount(); j++) {

            elementsRow2.add(new Element(j, 1));
        }
        elementsRow3.add(new Element(5, 2));
        
        assertFalse(glass.add(instance));
        assertTrue(obs.callsCount == 0);
    }

    @Test
    void add_matrixHasLongString() {

        var obs = new TestGlassObserver();
        glass.addGlassListener(obs);
        ElementsMatrix instance = new ElementsMatrix(3);
        ArrayList<Element> elementsRow1 = new ArrayList<>();
        ArrayList<Element> elementsRow2 = new ArrayList<>();
        ArrayList<Element> elementsRow3 = new ArrayList<>();

        for (int j = 0; j < glass.getColCount() + 10; j++) {

            elementsRow1.add(new Element(j, 0));
        }
        for (int j = 0; j < glass.getColCount(); j++) {

            elementsRow2.add(new Element(j, 1));
        }
        elementsRow3.add(new Element(5, 2));
        instance.add(elementsRow1, 0);
        instance.add(elementsRow2, 1);
        instance.add(elementsRow3, 2);
        
        assertFalse(glass.add(instance));
        assertTrue(obs.callsCount == 0);
    }

    @Test
    void remove_index() {

        var obs = new TestGlassObserver();
        glass.addGlassListener(obs);
        ElementsMatrix instance = new ElementsMatrix(3);
        ArrayList<Element> elementsRow1 = new ArrayList<>();
        ArrayList<Element> elementsRow2 = new ArrayList<>();
        ArrayList<Element> elementsRow3 = new ArrayList<>();

        for (int j = 0; j < glass.getColCount() - 1; j++) {

            elementsRow1.add(new Element(j, 0));
        }
        for (int j = 0; j < glass.getColCount() - 1; j++) {

            elementsRow2.add(new Element(j, 1));
        }
        elementsRow3.add(new Element(5, 2));
        instance.add(elementsRow1, 0);
        instance.add(elementsRow2, 1);
        instance.add(elementsRow3, 2);
        glass.add(instance);
        
        glass.remove(0, 2);

        assertTrue(glass.elementsCount(2) == 0);
        assertTrue(obs.callsCount == 3);
    }

    @Test
    void remove_element() {

        var obs = new TestGlassObserver();
        glass.addGlassListener(obs);
        ElementsMatrix instance = new ElementsMatrix(3);
        ArrayList<Element> elementsRow1 = new ArrayList<>();
        ArrayList<Element> elementsRow2 = new ArrayList<>();
        ArrayList<Element> elementsRow3 = new ArrayList<>();

        for (int j = 0; j < glass.getColCount() - 1; j++) {

            elementsRow1.add(new Element(j, 0));
        }
        for (int j = 0; j < glass.getColCount() - 1; j++) {

            elementsRow2.add(new Element(j, 1));
        }
        elementsRow3.add(new Element(5, 2));
        instance.add(elementsRow1, 0);
        instance.add(elementsRow2, 1);
        instance.add(elementsRow3, 2);
        glass.add(instance);

        glass.remove(new Element(5, 2));

        assertTrue(glass.elementsCount(2) == 0);
        assertTrue(obs.callsCount == 3);
    }

    @Test
    void remove_elements() {

        var obs = new TestGlassObserver();
        glass.addGlassListener(obs);
        ElementsMatrix instance = new ElementsMatrix(3);
        ArrayList<Element> elementsRow1 = new ArrayList<>();
        ArrayList<Element> elementsRow2 = new ArrayList<>();
        ArrayList<Element> elementsRow3 = new ArrayList<>();

        for (int j = 0; j < glass.getColCount() - 1; j++) {

            elementsRow1.add(new Element(j, 0));
        }
        for (int j = 0; j < glass.getColCount() - 1; j++) {

            elementsRow2.add(new Element(j, 1));
        }
        elementsRow3.add(new Element(5, 2));
        instance.add(elementsRow1, 0);
        instance.add(elementsRow2, 1);
        instance.add(elementsRow3, 2);
        glass.add(instance);
        ArrayList<Element> elementsToRemove = new ArrayList<>();
        elementsToRemove.add(new Element(0, 1));
        elementsToRemove.add(new Element(5, 2));
        glass.remove(elementsToRemove);

        assertTrue(glass.elementsCount() == 17);
        assertTrue(glass.elementsCount(2) == 0);
        assertTrue(glass.elementsCount(1) == 8);
        assertTrue(obs.callsCount == 3);
    }
    
    //Класс для тестирования сигналов, испущенных стаканом
    private class TestGlassObserver implements GlassListener {

        private int callsCount = 0;

        @Override
        public void glassFilled() { ++callsCount;}

        @Override
        public void needNewActiveShape() { ++callsCount; }

        @Override
        public void shapeAbsorbed() { ++callsCount; }

        @Override
        public void rowCleared(GlassEvent e) { ++callsCount; }

        @Override
        public void glassContentChanged() { ++callsCount; }

    }
/*
    
*/
}