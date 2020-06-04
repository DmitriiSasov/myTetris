package my_tetris.general_game_objects;

import my_tetris.Direction;
import my_tetris.Element;
import my_tetris.ElementsMatrix;

import java.util.ArrayList;
import java.util.Iterator;

public class SinkingShape extends AbstractShape {

    protected int currentDepth = 0;
    
    protected int maxDepth;
    
    public SinkingShape(int maxDepth, ElementsMatrix elements) {
        
        super(elements);
        this.maxDepth = maxDepth;
    }

    public SinkingShape(int maxDepth) { this.maxDepth = maxDepth; }

    public int getCurrentDepth() { return currentDepth; }

    public int getMaxDepth() { return maxDepth; }
    
    protected void changeCurrentDepth(int delta) { currentDepth += delta; }

    public boolean isSinkingStarted() { return currentDepth > 0; }
    
    public boolean isSinkingFinished() { return currentDepth == maxDepth; }
    
    protected void sink() {

        ArrayList<Element> commonElements = new ArrayList<>();
        Iterator<Element> iterator = elements.allElementsConstIterator();
        while (iterator.hasNext()) {
            
            Element tmp = iterator.next();
            if (glass.contains(tmp)) { commonElements.add(tmp); }
        }
        glass.remove(commonElements);
        
        changeCurrentDepth(1);
    }
    
    @Override
    protected boolean canRotate() {
        
        if (isSinkingStarted()) { return false; }
        
        return super.canRotate();
    }

    @Override
    protected void rotateElements() {
        
        super.rotateElements();
        if (glass.hasCommonElements(elements)) { sink(); }
    }

    @Override
    protected boolean canMove(Direction d) {
        
        if (isSinkingStarted() && d != Direction.SOUTH || isSinkingFinished()) { return false; }
        
        return super.canMove(d);
    }

    @Override
    protected void moveElements(Direction d) {
        
        super.moveElements(d);
        if (glass.hasCommonElements(elements) || isSinkingStarted()) { sink(); }
    }

    @Override
    protected boolean isCorrectPosition(ElementsMatrix elements) {
        
        Iterator<Element> iterator = elements.allElementsConstIterator();
        while (iterator.hasNext()) {

            if (super.glass.isElementOutsideGlassBorder(iterator.next())) { return false; }
        }

        return true;
    }

    @Override
    public AbstractShape clone() {

        SinkingShape shapeClone = null;
        try { 
            
            shapeClone = new SinkingShape(maxDepth); 
            if (elements != null) { shapeClone.setElements(elements.clone()); }
            shapeClone.currentDepth = currentDepth;
        }
        catch (CloneNotSupportedException e) { e.printStackTrace(); }
        shapeClone.glass = glass;
        
        return shapeClone;
    }
}
