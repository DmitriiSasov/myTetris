/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_tetris.general_game_objects;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import my_tetris.*;
import my_tetris.events.GlassEvent;
import my_tetris.events.GlassListener;

import javax.swing.text.AbstractDocument;

/**
 *
 * @author bopoh
 */
public class Glass implements ImmutableElementMatrix {

    public final int MIN_ROW_COUNT = 6;

    public final int MIN_COL_COUNT = 6;

    private AbstractShape activeShape;
    
    private ElementsMatrix elementsMatrix;

    private int rowCount;

    private int colCount;
    
    private Border border;

    public Glass(int colCount, int rowCount) {

        if (colCount < MIN_COL_COUNT || rowCount < MIN_ROW_COUNT) {

            throw new GlassSizeException("Размеры стакана слишком маленькие");
        }

        this.rowCount = rowCount;
        this.colCount = colCount;

        elementsMatrix = new ElementsMatrix(rowCount);

        border = new Border(0, 0, this.colCount -1);
    }

    public int getRowCount() { return rowCount; }

    public int getColCount() { return colCount; }

    public Border getBorder() { return border; }

    public AbstractShape getActiveShape() { return activeShape; }

    public void setActiveShape(AbstractShape shape) {

        if (activeShape != null) { activeShape.unsetGlass(); }
        
        activeShape = shape;

        if (activeShape.getGlass() != this) { activeShape.setGlass(this); }

        if (activeShape != null && activeShape.getGlass() == this) {
            
            activeShape.setStartPosition(0, rowCount - 1, 0, colCount - 1);
            //Проверить, что фигура находится внутри стакана
            Iterator<Element> iterator = activeShape.allElementsConstIterator();
            while (iterator.hasNext()) {

                if (border.isElementOutsideBorder(iterator.next())) {

                    throw new ActiveShapeOutOfGlassBorder("При установке в стакан фигура оказалась за его пределами");
                }
            }
        }
        
        /*
        activeShape.setStartPosition(0, rowCount - 1, 0, colCount - 1);

        boolean isCorrectStartPosition = true;
        for (int i = 0; i < activeShape.elementsCount() && isCorrectStartPosition; ++i) {

            Point tmpPosition = new Point(activeShape.getElement(i).getCol(), activeShape.getElement(i).getRow());
            isCorrectStartPosition = isCorrectStartPosition && isFreePosition(tmpPosition);
        }

        if (!isCorrectStartPosition) {

            unsetActiveShape();
            fireGlassFilled();
        }*/
    }

    public void unsetActiveShape() {

        var shape = activeShape;
        activeShape = null;

        if (shape != null && shape.getGlass() != null) { shape.unsetGlass(); }
    }

    public boolean isElementOutsideGlassBorder(Element e) { return border.isElementOutsideBorder(e); }
    
    private void clearFilledRows() {

        //Очистить пустые ряды
        for (int i = 0; i < rowCount && elementsCount(i) > 0; i++) {
            
            if (elementsCount(i) >= colCount) {
                
                int removedElementsCount = elementsMatrix.clear(i).size();//Очистить ряд
                //Сдвинуть непустые ряды выше текущего на 1 клетку вниз
                for (int j = i + 1; j < rowCount && elementsCount(j) > 0; j++) {
                    
                    ArrayList<Element> movedElements = elementsMatrix.clear(j);
                    for (Element e : movedElements) { e.move(Direction.SOUTH); }
                    elementsMatrix.add(movedElements, j - 1);                    
                }
                --i;
                GlassEvent evt = new GlassEvent(this);
                evt.setRemovedElementsCount(removedElementsCount);
                fireRowCleared(evt);
            }
        }
    }
    
    Element remove(int index, int rowIndex) { 
        
        Element tmp = elementsMatrix.remove(index,rowIndex);
        fireGlassContentChanged();
        return tmp; 
    }

    void remove(Element e) { 
        
        elementsMatrix.remove(e);
        fireGlassContentChanged();
    }
    
    void remove(ArrayList<Element> elements) { 
        
        elementsMatrix.remove(elements);
        fireGlassContentChanged();
    }
    
    boolean add(ElementsMatrix em) {

        if (em.rowCount() > elementsMatrix.rowCount()) { return false; }

        for (int i = 0; i < em.rowCount(); i++) {
            
            if (em.elementsCount(i) > colCount) { return false; } 
        }
        
        Iterator<Element> iterator = em.allElementsConstIterator();
        while (iterator.hasNext()) {
            
            Element tmp = iterator.next();
            if (!isElementOutsideGlassBorder(tmp)) {
                
                try { elementsMatrix.add(tmp.clone(), tmp.getRow()); } 
                catch (CloneNotSupportedException e) { e.printStackTrace(); }
            }
        }

        clearFilledRows();
        fireEventsOfShapeAdding();

        return true;
    }
    
    boolean add(Element e) {

        boolean res = false;
        
        if (!isElementOutsideGlassBorder(e)) {

            try { res = elementsMatrix.add(e.clone(), e.getRow()); } 
            catch (CloneNotSupportedException ex) { ex.printStackTrace(); }

            clearFilledRows();
            fireEventsOfShapeAdding();
        }

        return  res;
    }
    
    void add(ArrayList<Element> elements) {

        for (Element e: elements) {
            
            if (!isElementOutsideGlassBorder(e)) {
                
                try { elementsMatrix.add(e.clone(), e.getRow()); } 
                catch (CloneNotSupportedException ex) { ex.printStackTrace(); }
            }
        }
        
        
        clearFilledRows();
        fireEventsOfShapeAdding();
        
    }
    
    private void fireEventsOfShapeAdding() {
        
        fireShapeAbsorbed();
        System.out.println("Фигура поглощена");//Для вывода на экран

        if (isHighestRowFilled()) { fireGlassFilled(); }
        else { fireNeedNewActiveShape(); }
    }
    
    void clear() { elementsMatrix.clear(); }
    
    ArrayList<Element> clear(int rowIndex) { return elementsMatrix.clear(rowIndex); }
    
    Element getElement(int index, int rowIndex) { return elementsMatrix.getElement(index, rowIndex); }
     
    private boolean isHighestRowFilled() { return elementsMatrix.elementsCount(rowCount - 1) > 0; }

    @Override
    public Iterator<Element> allElementsConstIterator() { return elementsMatrix.allElementsConstIterator(); }

    @Override
    public Iterator<Element> constIteratorByRow(int index) { return elementsMatrix.constIteratorByRow(index); }

    @Override
    public Element getElementCopy(int index, int rowIndex) {

        try { return elementsMatrix.getElement(index, rowIndex).clone(); }
        catch (CloneNotSupportedException e) { e.printStackTrace(); }

        return null;
    }

    @Override
    public int elementsCount() { return elementsMatrix.elementsCount(); }

    @Override
    public int elementsCount(int rowIndex) { return elementsMatrix.elementsCount(rowIndex); }

    @Override
    public boolean contains(Element e) { return elementsMatrix.contains(e); }

    @Override
    public boolean hasCommonElements(ElementsMatrix em) { return elementsMatrix.hasCommonElements(em); }


    //Сообщает игре об перемещении фигуры и обновлении рядов, также о заполненности
    private ArrayList<GlassListener> glassListeners = new ArrayList<>();

    public void addGlassListener(GlassListener l) { if (!glassListeners.contains(l)) { glassListeners.add(l); } }

    public void removeGlassListener(GlassListener l) { glassListeners.remove(l); }

    public void fireGlassFilled() {

        for (GlassListener l : glassListeners) {

            System.out.println("Стакан заполнен");//Для вывода на экран
            l.glassFilled();
        }
    }

    public void fireNeedNewActiveShape() {

        for (GlassListener l : glassListeners) { l.needNewActiveShape(); }
    }
    
    public void fireShapeAbsorbed() {

        for (GlassListener l: glassListeners) { l.shapeAbsorbed(); }
    }

    public void fireRowCleared(GlassEvent e) {

        for (GlassListener l : glassListeners) { l.rowCleared(e);}
    }
    
    public void fireGlassContentChanged() {

        for (GlassListener l : glassListeners) { l.glassContentChanged(); }
    }
    
   
    
    
    public class ActiveShapeOutOfGlassBorder extends IllegalStateException {

        public ActiveShapeOutOfGlassBorder(String s) { super(s); }
    }
    
    public class GlassSizeException extends IllegalArgumentException {

        public GlassSizeException(String s) { super(s); }
    }

    public class EqualElementsInGlassException extends IllegalStateException {

        public EqualElementsInGlassException(String s) { super(s); }
    }
}
