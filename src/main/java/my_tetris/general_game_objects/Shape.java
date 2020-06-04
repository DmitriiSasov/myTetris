package my_tetris.general_game_objects;

import my_tetris.Element;
import my_tetris.ElementsMatrix;

import java.util.ArrayList;
import java.util.Iterator;

public class Shape extends AbstractShape {

    public Shape(ElementsMatrix elements) {
        super(elements);
    }

    public Shape() {
    }

    @Override
    protected boolean isCorrectPosition(ElementsMatrix elements) {
        
        if(super.glass.hasCommonElements(elements)) { return false; }

        Iterator<Element> iterator = elements.allElementsConstIterator();
        while (iterator.hasNext()) {
            
            if (super.glass.isElementOutsideGlassBorder(iterator.next())) { return false; }
        }
        
        return true;
    }

    @Override
    public Shape clone() {

        Shape shapeClone = null;
        try { 
            
            shapeClone = new Shape();
            if (elements != null) { shapeClone.setElements(elements.clone()); }
        } 
        catch (CloneNotSupportedException e) { e.printStackTrace(); }
        shapeClone.glass = glass;
        
        return shapeClone;
    }    
}
