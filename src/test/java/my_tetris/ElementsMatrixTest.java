package my_tetris;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ElementsMatrixTest {

    private boolean equals(Element[] elements1, Element[] elements2) {
        
        if (elements1 == elements2) {
            return true;
        }
        if (elements1.length != elements2.length) {
            return false;
        }
        for (int i = 0; i < elements1.length; i++) {
            if (!elements1[i].equals(elements2[i])) { return false; }
        }
        
        return true;
    }
    
    private boolean equals(ElementsMatrix em1, ElementsMatrix em2) {
        
        if (em1 == em2) {
            return true;
        }
        if (em1.elementsCount() != em2.elementsCount() || em1.rowCount() != em2.rowCount()) {
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
    
    @Test
    void constructor_invalidRowCount() {

        try { 
            new ElementsMatrix(-1);
            assertTrue(false);
        }
        catch (ElementsMatrix.ElementsMatrixSizeException e) { assertTrue(true);}
    }

    @Test
    void constructor() {

        try { new ElementsMatrix(1); }
        catch (ElementsMatrix.ElementsMatrixSizeException e) { assertTrue(false);}

        assertTrue(true);
    }
    
    @Test
    void rowCount() {
        
        ElementsMatrix instance = new ElementsMatrix(5);
        int expectedResult = 5;
        int result = instance.rowCount();
        
        assertEquals(expectedResult, result);
    }

    @Test
    void remove_invalidIndex() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 4;
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        Element removedElement = instance.remove(4, 1);

        assertNull(removedElement);
        assertTrue(expectedResult == instance.elementsCount());
    }

    @Test
    void remove_invalidRowIndex() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 4;
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        Element removedElement = instance.remove(0, 5);

        assertNull(removedElement);
        assertTrue(expectedResult == instance.elementsCount());
    }

    @Test
    void remove() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 3;
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        Element removedElement = instance.remove(0, 4);

        assertEquals(removedElement, e3);
        assertTrue(expectedResult == instance.elementsCount());
    }
    
    @Test
    void testRemove() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 3;
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        instance.remove(e3);

        assertTrue(expectedResult == instance.elementsCount());
    }

    @Test
    void testRemove1_removeAllElements() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ArrayList<Element> elementsToRemove = new ArrayList<>();
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 0;
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        elementsToRemove.add(e);
        elementsToRemove.add(e1);
        elementsToRemove.add(e2);
        elementsToRemove.add(e3);
        instance.remove(elementsToRemove);

        assertTrue(expectedResult == instance.elementsCount());
    }

    @Test
    void testRemove1_removeSomeElements() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ArrayList<Element> elementsToRemove = new ArrayList<>();
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 1;
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        elementsToRemove.add(e);
        elementsToRemove.add(e1);
        elementsToRemove.add(e2);
        elementsToRemove.add(e2);
        instance.remove(elementsToRemove);

        assertTrue(expectedResult == instance.elementsCount());
    }
    
    @Test
    void add() {
        
        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,4);
        Element expectedResult = e;
        boolean isAdded = instance.add(e, 3);

        Element result = instance.getElement(0, 3);
        
        assertTrue(isAdded);
        assertTrue(expectedResult == result);
    }

    @Test
    void add_invalidRowIndex() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,3);
        Element expectedResult = null;
        boolean isAdded = instance.add(e, 5);

        Element result = instance.getElement(0, 3);

        assertFalse(isAdded);
        assertTrue(expectedResult == result);
    }

    @Test
    void add_containsEqualElement() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,3);
        instance.add(e, 4);
        
        Element expectedResult = null;
        boolean isAdded = instance.add(e, 3);

        Element result = instance.getElement(0, 3);

        assertFalse(isAdded);
        assertTrue(expectedResult == result);
    }
    
    @Test
    void testAdd_noEqualElements() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ElementsMatrix matrix = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 4;
        int expectedResult_2 = 2;
        instance.add(e, 4);
        instance.add(e1, 1);
        matrix.add(e2, 4);
        matrix.add(e3, 1);

        boolean isAdded = instance.add(matrix);

        assertTrue(isAdded);
        assertTrue(expectedResult == instance.elementsCount());
        assertTrue(expectedResult_2 == matrix.elementsCount());
    }

    @Test
    void testAdd_addTooBigMatrix() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ElementsMatrix matrix = new ElementsMatrix(6);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 2;
        int expectedResult_2 = 2;
        instance.add(e, 4);
        instance.add(e1, 1);
        matrix.add(e2, 4);
        matrix.add(e3, 1);

        boolean isAdded = instance.add(matrix);

        assertFalse(isAdded);
        assertTrue(expectedResult == instance.elementsCount());
        assertTrue(expectedResult_2 == matrix.elementsCount());
    }

    @Test
    void testAdd_addElementsWithRepits() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ElementsMatrix matrix = new ElementsMatrix(4);
        Element e = new Element(1,10);
        Element e1 = new Element(1,8);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 3;
        int expectedResult_2 = 2;
        instance.add(e, 4);
        instance.add(e1, 1);
        matrix.add(e2, 3);
        matrix.add(e3, 1);

        boolean isAdded = instance.add(matrix);

        assertTrue(isAdded);
        assertTrue(expectedResult == instance.elementsCount());
        assertTrue(expectedResult_2 == matrix.elementsCount());
    }
    
    @Test
    void testAdd1_InvalidRowIndex() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ArrayList<Element> elements = new ArrayList<>();
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 2;
        int expectedResult_2 = 2;
        instance.add(e, 4);
        instance.add(e1, 1);
        elements.add(e2);
        elements.add(e3);

        boolean isAdded = instance.add(elements, 5);

        assertFalse(isAdded);
        assertTrue(expectedResult == instance.elementsCount());
        assertTrue(expectedResult_2 == elements.size());
    }

    @Test
    void testAdd1_noEqualElements() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ArrayList<Element> elements = new ArrayList<>();
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 4;
        int expectedResult_2 = 2;
        instance.add(e, 4);
        instance.add(e1, 1);
        elements.add(e2);
        elements.add(e3);

        boolean isAdded = instance.add(elements, 3);

        assertTrue(isAdded);
        assertTrue(expectedResult == instance.elementsCount());
        assertTrue(expectedResult_2 == elements.size());
    }

    @Test
    void testAdd1_equalElementsInArrayAndMatrix() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ArrayList<Element> elements = new ArrayList<>();
        Element e = new Element(1,10);
        Element e1 = new Element(1,8);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 3;
        int expectedResult_2 = 2;
        instance.add(e, 4);
        instance.add(e1, 1);
        elements.add(e2);
        elements.add(e3);

        boolean isAdded = instance.add(elements, 3);

        assertTrue(isAdded);
        assertTrue(expectedResult == instance.elementsCount());
        assertTrue(expectedResult_2 == elements.size());
    }

    @Test
    void testAdd1_equalElementsInArray() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ArrayList<Element> elements = new ArrayList<>();
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,8);
        int expectedResult = 3;
        int expectedResult_2 = 2;
        instance.add(e, 4);
        instance.add(e1, 1);
        elements.add(e2);
        elements.add(e3);

        boolean isAdded = instance.add(elements, 3);

        assertTrue(isAdded);
        assertTrue(expectedResult == instance.elementsCount());
        assertTrue(expectedResult_2 == elements.size());
    }
    
    @Test
    void move() {
        
        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        Element[] expectedResult = {new Element(1,9), new Element(1,8), new Element(1,7),
                new Element(1,6)};
        instance.move(Direction.SOUTH);

        Element[] result = {instance.getElement(0,1), instance.getElement(0,2),
                instance.getElement(0,3), instance.getElement(0,4)};

        assertTrue(equals(expectedResult, result));
        
    }

    @Test
    void rotate() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        Element[] expectedResult = {new Element(2,9), new Element(1,9), new Element(0,9),
                new Element(-1,9)};
        instance.rotate();

        Element[] result = {instance.getElement(0,1), instance.getElement(0,2),
                instance.getElement(0,3), instance.getElement(0,4)};

        assertTrue(equals(expectedResult, result));
    }   
    
    @Test
    void clear() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 0;
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        instance.clear();

        assertTrue(expectedResult == instance.elementsCount());
    }

    @Test
    void testClear() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 3;
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        instance.clear(4);

        assertTrue(expectedResult == instance.elementsCount());
    }

    @Test
    void getElement_invalidIndex() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        Element removedElement = instance.getElement(4, 1);

        assertNull(removedElement);
    }

    @Test
    void getElement_invalidRowIndex() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        Element removedElement = instance.getElement(0, 5);

        assertNull(removedElement);
    }

    @Test
    void getElement() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        Element element = instance.getElement(0, 1);

        assertTrue(element.equals(e));
    }
    
    @Test
    void contains_not() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        
        assertFalse(instance.contains(e3));
    }

    @Test
    void contains_yes() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);

        try { assertTrue(instance.contains(e3.clone())); }
        catch (CloneNotSupportedException ex) { ex.printStackTrace(); }
    }
    
    @Test
    void elementsCount() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 4;
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);

        assertTrue(expectedResult == instance.elementsCount());
    }

    @Test
    void testElementsCount() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        int expectedResult = 2;
        instance.add(e, 1);
        instance.add(e1, 1);
        instance.add(e2, 3);
        instance.add(e3, 3);

        assertTrue(expectedResult == instance.elementsCount(1));
    }

    @Test
    void iteratorByRow_invalidIndex() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 1);
        instance.add(e1, 1);
        instance.add(e2, 3);
        instance.add(e3, 3);        

        assertNull(instance.iteratorByRow(5));
    }

    @Test
    void iteratorByRow() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        Element expectedResult = new Element(1,9);
        instance.add(e, 1);
        instance.add(e1, 1);
        instance.add(e2, 3);
        instance.add(e3, 3);

        instance.iteratorByRow(1).next().move(Direction.SOUTH);
        
        assertTrue(expectedResult.equals(e));
    }
    
    @Test
    void allElementsIterator() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        Element expectedResult = new Element(1,9);
        instance.add(e, 1);
        instance.add(e1, 1);
        instance.add(e2, 3);
        instance.add(e3, 3);

        instance.allElementsIterator().next().move(Direction.SOUTH);

        assertTrue(expectedResult.equals(e));
    }

    @Test
    void constIteratorByRow() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        Element expectedResult = new Element(1,10);
        instance.add(e, 1);
        instance.add(e1, 1);
        instance.add(e2, 3);
        instance.add(e3, 3);

        instance.constIteratorByRow(1).next().move(Direction.SOUTH);

        assertTrue(expectedResult.equals(e));
    }

    @Test
    void allElementsConstIterator() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        Element expectedResult = new Element(1,10);
        instance.add(e, 1);
        instance.add(e1, 1);
        instance.add(e2, 3);
        instance.add(e3, 3);

        instance.allElementsConstIterator().next().move(Direction.SOUTH);

        assertTrue(expectedResult.equals(e));
    }

    @Test
    void hasCommonElements_yes() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ElementsMatrix matrix = new ElementsMatrix(4);
        Element e = new Element(1,10);
        Element e1 = new Element(1,8);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 4);
        instance.add(e1, 1);
        matrix.add(e2, 3);
        matrix.add(e3, 1);

        assertTrue(instance.hasCommonElements(matrix));
    }

    @Test
    void hasCommonElements_no() {

        ElementsMatrix instance = new ElementsMatrix(5);
        ElementsMatrix matrix = new ElementsMatrix(4);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        Element e2 = new Element(1,8);
        Element e3 = new Element(1,7);
        instance.add(e, 4);
        instance.add(e1, 1);
        matrix.add(e2, 3);
        matrix.add(e3, 1);

        assertFalse(instance.hasCommonElements(matrix));
    }
    
    @Test
    void setStartPosition() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(2,9);
        Element e2 = new Element(3,8);
        Element e3 = new Element(4,7);
        instance.add(e, 1);
        instance.add(e1, 2);
        instance.add(e2, 3);
        instance.add(e3, 4);
        ElementsMatrix matrixInStartPos = new ElementsMatrix(5);
        e = new Element(3,0);
        e1 = new Element(4,-1);
        e2 = new Element(5,-2);
        e3 = new Element(6,-3);
        matrixInStartPos.add(e, 1);
        matrixInStartPos.add(e1, 2);
        matrixInStartPos.add(e2, 3);
        matrixInStartPos.add(e3, 4);
        
        instance.setStartPosition(-10, 4, 9);
        
        assertTrue(equals(instance, matrixInStartPos));
    }

    @Test
    void testClone() {

        ElementsMatrix instance = new ElementsMatrix(5);
        Element e = new Element(1,10);
        Element e1 = new Element(1,9);
        instance.add(e, 4);
        instance.add(e1, 1);
        ElementsMatrix clone = null;
        try { clone = instance.clone(); } 
        catch (CloneNotSupportedException ex) { ex.printStackTrace(); }
        assertTrue(equals(instance, clone));
        assertFalse(instance == clone);
    }
}