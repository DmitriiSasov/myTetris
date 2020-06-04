/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_tetris.general_game_objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import my_tetris.*;
import my_tetris.events.ShapeListener;

/**
 *
 * @author bopoh
 */
public abstract class AbstractShape implements Cloneable, ImmutableElementMatrix {

    protected Glass glass = null;

    protected ElementsMatrix elements = null;

    public AbstractShape(ElementsMatrix elements) {

        try { this.elements = elements.clone(); } 
        catch (CloneNotSupportedException e) {e.printStackTrace(); }
    }

    public AbstractShape() {}

    //Элементы устанавливаются 1 раз после создания фигуры, если у фигуры не задана матрица
    public void setElements(ElementsMatrix em) {
        
        if (elements == null) {
            
            try { this.elements = em.clone(); }
            catch (CloneNotSupportedException e) { e.printStackTrace(); }
        }        
    }

    protected void setGlass(Glass glass) {

        if (this.glass != null) { this.glass.unsetActiveShape(); }

        this.glass = glass;

        if (glass.getActiveShape() != this) { glass.setActiveShape(this); }
    }
    
    protected Glass getGlass() { return glass; }

    protected void unsetGlass() {

        var glass = this.glass;
        this.glass = null;
        if (glass != null && glass.getActiveShape() != null) { glass.unsetActiveShape(); }
    }
    
    Element getElement(int index, int rowIndex) { return elements.getElement(index, rowIndex); }

    @Override
    public Element getElementCopy(int index, int rowIndex) {

        try { return elements.getElement(index, rowIndex).clone(); } 
        catch (CloneNotSupportedException e) { e.printStackTrace(); }
        
        return null;
    }

    protected Element removeElement(int index, int rowIndex) { return elements.remove(index, rowIndex); }
    
    public void rotate() {
        
        if (canRotate()) {
            
            rotateElements();
            System.err.println("Фигура повернулась");//Для вывода на экран
            fireShapeLocationChanged();
        }
    }

    protected boolean canRotate() {

        ElementsMatrix copy = null;
        try { copy = elements.clone(); }
        catch (CloneNotSupportedException e) { e.printStackTrace(); }

        copy.rotate();

        return isCorrectPosition(copy);
    }
    
    protected void rotateElements() { elements.rotate(); }

    protected void setStartPosition(int minRowIndex, int maxRowIndex, int minColIndex, int maxColIndex) {
        
        boolean isCorrectSpaceSize = true;
        if (elements.rowCount() < maxRowIndex - minRowIndex ) {

            for (int i = 0; i < elements.rowCount(); i++) {
                isCorrectSpaceSize = isCorrectSpaceSize && elements.elementsCount(i) < maxColIndex - minColIndex &&
                        elements.elementsCount(i) < maxRowIndex - minRowIndex;
            } 
        } else {
            
            isCorrectSpaceSize = false;
        }
        
        if (!isCorrectSpaceSize) {
            
            throw new ShapeStartPositionException("Пространство для размещения фигуры слишком маленькое");
        }
        
        elements.setStartPosition(maxRowIndex, minColIndex, maxColIndex);

        //Проверить, что все элементы фигуры находятся внутри стакана и самые нижние элементы фигуры находятся
        //на уровне верхней границы стакана
        Iterator<Element> iterator = allElementsConstIterator();
        while(iterator.hasNext()) {
            
            Element tmp = iterator.next();
            if (tmp.getRow() < maxRowIndex || tmp.getCol() < minColIndex || tmp.getCol() > maxColIndex) {
                
                throw new ShapeStartPositionException("Шаблон задан неверно, и фигура оказалась не в той части стакана");
            }
        }
        
        
        fireShapeReadyToMove();
        fireShapeLocationChanged();

        System.out.println("Фигура установлена в верхушке стакана");//Для вывода на экран
    }

    public boolean hasElements() { return elementsCount() > 0; }

    public int elementsCount() { return elements.elementsCount(); }
    
    synchronized public void move(Direction d) {
                
        boolean canMove = canMove(d);
        
        if (canMove) {
            
            moveElements(d);
            System.err.println("Фигура передвинулась на 1 клетку на " + d.toString());//Для вывода на экран
            fireShapeLocationChanged();
        }
        
        if (d == Direction.SOUTH && !canMove) {

            System.err.println("Фигура остановилась");//Для вывода на экран
            fireShapeStopped();
            glass.add(elements);
            clear();
        }
    }

    protected boolean canMove(Direction d) {
        
        ElementsMatrix copy = null;
        try { copy = elements.clone(); } 
        catch (CloneNotSupportedException e) { e.printStackTrace(); }
        
        copy.move(d);
        
        return isCorrectPosition(copy);
    }
    
    protected void moveElements(Direction d) { elements.move(d); }

    protected abstract boolean isCorrectPosition(ElementsMatrix elements);

    protected void clear() { elements.clear(); }
    
    protected ArrayList<Element> clear(int index) { return elements.clear(index); }
    
    @Override
    public boolean contains(Element e) { return elements.contains(e); }

    @Override
    public boolean hasCommonElements(ElementsMatrix em) { return elements.hasCommonElements(em); }
    
    @Override
    public Iterator<Element> allElementsConstIterator() { return elements.allElementsConstIterator() ;}

    @Override
    public Iterator<Element> constIteratorByRow(int index) { return elements.constIteratorByRow(index); }

    @Override
    public int elementsCount(int rowIndex) { return elements.elementsCount(rowIndex); }

    public abstract AbstractShape clone();
    
    //Для вывода на экран
    @Override
    public String toString() {

        String res = "Позиция фигуры: ";

        Iterator<Element> iterator = elements.allElementsConstIterator();
        while (iterator.hasNext()) { res += iterator.next().toString(); }

        return res;
    }
    
    //Отправка событий слушателям
    protected ArrayList<ShapeListener> listeners = new ArrayList<>();
    
    public void addShapeListener(ShapeListener l) { listeners.add(l); }
    
    public void removeShapeListener(ShapeListener l) { listeners.remove(l); }
    
    protected void fireShapeReadyToMove() {

        for (ShapeListener l: listeners) { l.shapeReadyToMove(); }
    }
    
    protected void fireShapeLocationChanged() {

        for (ShapeListener l : listeners) { l.shapeLocationChanged(); }
    }
    
    protected void fireShapeStopped() {

        for (ShapeListener l : listeners) { l.shapeStopped(); }
    }
    
    public class ShapeStartPositionException extends IllegalArgumentException {

        public ShapeStartPositionException(String s) { super(s); }
    }
}
